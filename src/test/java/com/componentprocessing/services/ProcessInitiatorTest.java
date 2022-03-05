package com.componentprocessing.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.componentprocessing.dao.ProcessResponseRepository;
import com.componentprocessing.dao.UserRequestRepo;
import com.componentprocessing.exceptions.ComponentNotIdentifiedException;
import com.componentprocessing.exceptions.ProcessingFailedException;
import com.componentprocessing.exceptions.ValidationFailedException;
import com.componentprocessing.model.DefectiveComponentDetail;
import com.componentprocessing.model.ProcessRequest;
import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.model.UserRequests;
import com.componentprocessing.servicesimpl.ProcessInitiatorImpl;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = { ProcessInitiator.class,
		ProcessInitiatorImpl.class })
class ProcessInitiatorTest {

	@MockBean
	PackagingAndDeliveryService packagingAndDeliveryService;
	@MockBean
	ProcessResponseRepository processResponseRepository;
	@MockBean
	UserRequestRepo userRequestsRepository;
	@Mock
	ProcessService processService;

	@Autowired
	ProcessInitiator processInitiator;

	DefectiveComponentDetail defectiveComponentDetail(String componentType) {
		return new DefectiveComponentDetail("Mobile", componentType, 5);
	}

	UserRequests userRequests() {
		return new UserRequests("tester", 200L);
	}

	ProcessRequest processRequest(String componentType) {
		return new ProcessRequest("tester", 123456789L, defectiveComponentDetail(componentType));
	}

	@Test
	@DirtiesContext
	void testProcessInitiatorIntegralitem() {
		ProcessRequest processRequestObject = processRequest("INTEGRAL");
		when(packagingAndDeliveryService.getPackagingAndDeliveryCharge("INTEGRAL", 5)).thenReturn(200L);
		ProcessResponse processResponseObject = processResponse2();
		when(processResponseRepository.save(any())).thenReturn(processResponseObject);
		when(processService.fetchReturnOrderdeatils(200L, 5)).thenReturn(processResponseObject);
		when(userRequestsRepository.saveUserRequest(any())).thenReturn(userRequests());
		ProcessResponse extractedProcessReturnOrder = processInitiator.processReturnOrder(processRequestObject);
		assertEquals(processResponseObject, extractedProcessReturnOrder);

	}

	@Test
	@DirtiesContext
	void testProcessInitiatorAccessory() {
		ProcessRequest processRequestObject = processRequest("ACCESSORY");
		when(packagingAndDeliveryService.getPackagingAndDeliveryCharge("ACCESSORY", 5)).thenReturn(200L);
		ProcessResponse processResponseObject = processResponse2();
		when(processResponseRepository.save(any())).thenReturn(processResponseObject);
		when(processService.fetchReturnOrderdeatils(200L, 5)).thenReturn(processResponseObject);
		when(userRequestsRepository.saveUserRequest(any())).thenReturn(userRequests());
		ProcessResponse extractedProcessReturnOrder = processInitiator.processReturnOrder(processRequestObject);
		assertEquals(processResponseObject, extractedProcessReturnOrder);

	}

	@Test
	void testProcessInitiatorFeignFailure() {
		ProcessRequest processRequestObject = processRequest("ACCESSORY2");
		when(packagingAndDeliveryService.getPackagingAndDeliveryCharge("ACCESSORY2", 5))
				.thenThrow(feign.FeignException.class);
		ProcessResponse processResponseObject = processResponse();
		when(processResponseRepository.save(any())).thenReturn(processResponseObject);
		when(processService.fetchReturnOrderdeatils(200L, 5)).thenReturn(processResponseObject);
		assertThatExceptionOfType(ProcessingFailedException.class)
				.isThrownBy(() -> processInitiator.processReturnOrder(processRequestObject));

	}

	@Test
	void testProcessInitiatorResponseSaveFailed() {
		ProcessRequest processRequestObject = processRequest("ACCESSORY");
		when(packagingAndDeliveryService.getPackagingAndDeliveryCharge("ACCESSORY", 5)).thenReturn(200L);
		ProcessResponse processResponseObject = processResponse();
		when(processResponseRepository.save(any())).thenThrow(RuntimeException.class);
		when(processService.fetchReturnOrderdeatils(200L, 5)).thenReturn(processResponseObject);
		assertThatExceptionOfType(ProcessingFailedException.class)
				.isThrownBy(() -> processInitiator.processReturnOrder(processRequestObject));

	}

	@Test
	void testProcessInitiatorComponentProcessingNotAvailable() {
		ProcessRequest processRequestObject = processRequest("ACCESSORY2");
		when(packagingAndDeliveryService.getPackagingAndDeliveryCharge("ACCESSORY2", 5)).thenReturn(200L);
		ProcessResponse processResponseObject = processResponse();
		when(processResponseRepository.save(any())).thenReturn(processResponseObject);
		when(processService.fetchReturnOrderdeatils(200L, 5)).thenReturn(processResponseObject);
		assertThatExceptionOfType(ComponentNotIdentifiedException.class)
				.isThrownBy(() -> processInitiator.processReturnOrder(processRequestObject));

	}

	ProcessResponse processResponse() {
		return new ProcessResponse(200L, 200L, new Date());
	}

	ProcessResponse processResponse2() {
		return new ProcessResponse(10001L, 200L, 200L, new Date());
	}

	@Test
	void testValidateResult_Fail() {
		ProcessRequest processRequestObject = processRequest("INTEGRAL");
		processRequestObject.setName(null);
		assertThatExceptionOfType(ValidationFailedException.class)
				.isThrownBy(() -> processInitiator.validateProcessRequestObject(processRequestObject));

	}

	@Test
	void testValidateResult() {
		ProcessRequest processRequestObject = processRequest("INTEGRAL");

		assertTrue(processInitiator.validateProcessRequestObject(processRequestObject));

	}

}
