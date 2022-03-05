package com.componentprocessing.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.componentprocessing.exceptionhandlers.RequestContext;
import com.componentprocessing.model.ConfirmReturn;
import com.componentprocessing.model.ConfirmReturnRequest;
import com.componentprocessing.model.ProcessRequest;
import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.proxy.AuthorizationContext;
import com.componentprocessing.services.OrderDetailsService;
import com.componentprocessing.services.ProcessCompletionService;
import com.componentprocessing.services.ProcessInitiator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
@CrossOrigin
public class ComponentProcessingController {

	@Autowired
	ProcessInitiator processInitiator;

	@Autowired
	private RequestContext requestContext;

	@Autowired
	ProcessCompletionService processCompletionService;

	@Autowired
	AuthorizationContext authorizationContext;

	@Autowired
	OrderDetailsService orderDetailsService;

	@GetMapping("/ProcessDetail")
	public ProcessResponse getProcessDetail(@Valid @RequestParam String processRequest,
			@RequestHeader(value = "Authorization") String authorization) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ProcessRequest processRequestObject = objectMapper.readValue(processRequest, ProcessRequest.class);
		authorizationContext.setAuthorization(authorization);
		log.info("validating the new request {}", processRequest);
		if (processInitiator.validateProcessRequestObject(processRequestObject)) {
			log.info("validation success !");
		}
		log.info("new request successfully recieved for returning component on :{}", new Date());
		requestContext.setRequestBody(processRequestObject);
		return processInitiator.processReturnOrder(processRequestObject);

	}

	@PostMapping("/CompleteProcessing")
	@ResponseBody
	public String saveConfirmedOrder(@Valid @RequestBody ConfirmReturnRequest confirmReturnRequest) {

		requestContext.setRequestBody(confirmReturnRequest);
		return processCompletionService.completeProcess(confirmReturnRequest);

	}

	@GetMapping("/getOrderDetails")
	public List<ConfirmReturn> getOrderDetailsForUser(@Valid @NotNull @RequestParam String name) {
		log.info("recieved request to get all order details of the user {}", name);
		return orderDetailsService.findOrderDetails(name);

	}

}
