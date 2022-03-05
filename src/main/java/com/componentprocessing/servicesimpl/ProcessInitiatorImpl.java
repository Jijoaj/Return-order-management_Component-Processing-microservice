package com.componentprocessing.servicesimpl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.dao.ProcessResponseRepository;
import com.componentprocessing.dao.UserRequestRepo;
import com.componentprocessing.exceptions.ComponentNotIdentifiedException;
import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;
import com.componentprocessing.exceptions.ProcessingFailedException;
import com.componentprocessing.exceptions.ValidationFailedException;
import com.componentprocessing.model.ProcessRequest;
import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.model.UserRequests;
import com.componentprocessing.services.PackagingAndDeliveryService;
import com.componentprocessing.services.ProcessInitiator;
import com.componentprocessing.services.ProcessService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessInitiatorImpl implements ProcessInitiator {
	@Autowired
	PackagingAndDeliveryService packagingAndDeliveryService;
	@Autowired
	ProcessResponseRepository processResponseRepository;

	@Autowired
	UserRequestRepo userRequestsRepository;

	ProcessService processService;

	@Override
	public ProcessResponse processReturnOrder(ProcessRequest processRequest) {
		try {

			log.info("initiating return order details fetching");

			String componentType = processRequest.getDefectiveComponentDetail().getComponentType().toUpperCase();
			log.info("component type from request -->{}", componentType);

			Integer quantity = processRequest.getDefectiveComponentDetail().getQuantity();
			log.info("quantity from request -->{}", quantity);

			Long packagingAndDeliveryCharge = packagingAndDeliveryService.getPackagingAndDeliveryCharge(componentType,
					quantity);
			log.info("packaging and delivery charge for the component -> {}", packagingAndDeliveryCharge);

			if (componentType.equals("INTEGRAL")) {
				log.info("fetching processing duration and processing charge for integral item");
				processService = new RepairIntegralPartService();
				ProcessResponse processResponse = processResponseRepository
						.save(processService.fetchReturnOrderdeatils(packagingAndDeliveryCharge, quantity));
				log.info("process response ready");
				userRequestsRepository
						.saveUserRequest(new UserRequests(processRequest.getName(), processResponse.getRequestId()));
				log.info("user request saved");
				return processResponse;

			} else if (componentType.equals("ACCESSORY")) {
				log.info("fetching processing duration and processing charge for accessory");
				processService = new ReplacementForAccessoryService();
				ProcessResponse processResponse = processResponseRepository
						.save(processService.fetchReturnOrderdeatils(packagingAndDeliveryCharge, quantity));
				log.info("process response ready");
				userRequestsRepository
						.saveUserRequest(new UserRequests(processRequest.getName(), processResponse.getRequestId()));

				log.info("user request saved");
				return processResponse;

			} else {
				log.error("the component mentioned in  request is neither accessory nor an integral part");
				throw new ComponentNotIdentifiedException(componentType + " not identified");
			}

		} catch (ComponentNotIdentifiedException | PackagingAndDeliverChargeServiceException e) {
			throw e;
		}

		catch (Exception e) {
			log.error("processing failed due to an unknown error");
			e.printStackTrace();
			throw new ProcessingFailedException();
		}

	}

	public boolean validateProcessRequestObject(ProcessRequest processRequestObject) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProcessRequest>> violations = validator.validate(processRequestObject);
		for (ConstraintViolation<ProcessRequest> violation : violations) {
			if (!violation.getMessage().isEmpty()) {
				log.error("violation detected in request : {}", violation.getMessage());
				throw new ValidationFailedException(violation.getMessage());
			}
		}
		return true;
	}

}
