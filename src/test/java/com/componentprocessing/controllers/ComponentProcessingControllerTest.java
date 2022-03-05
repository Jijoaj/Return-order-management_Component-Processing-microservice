package com.componentprocessing.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.componentprocessing.exceptions.ComponentNotIdentifiedException;
import com.componentprocessing.exceptions.ConfirmationFailedException;
import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;
import com.componentprocessing.exceptions.ProcessingFailedException;
import com.componentprocessing.exceptions.ValidationFailedException;
import com.componentprocessing.model.ConfirmReturnRequest;
import com.componentprocessing.model.DefectiveComponentDetail;
import com.componentprocessing.model.ProcessRequest;
import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.services.ProcessCompletionService;
import com.componentprocessing.services.ProcessInitiator;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ComponentProcessingControllerTest {

	@MockBean
	ProcessInitiator processInitiator;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProcessCompletionService processCompletionService;

	DefectiveComponentDetail defectiveComponentDetail(String componentType) {
		return new DefectiveComponentDetail("Mobile", componentType, 5);
	}

	ProcessRequest processRequest(String componentType) {
		return new ProcessRequest("tester", 123456789L, defectiveComponentDetail(componentType));
	}

	ProcessResponse processResponse() {
		return new ProcessResponse(10001L, 200L, 200L, "12-12-12");
	}

	@Test
	void testGetProcessDetail() throws Exception {
		ProcessRequest processRequestObject = processRequest("INTEGRAL ITEM");
		ProcessResponse processResponseObject = processResponse();

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(processRequestObject);
		String jsonStringResponse = map.writeValueAsString(processResponseObject);
		when(processInitiator.validateProcessRequestObject(any())).thenReturn(true);
		when(processInitiator.processReturnOrder(processRequestObject)).thenReturn(processResponseObject);
		this.mockMvc
				.perform(get("/ProcessDetail").header("Authorization", "test token")
						.param("processRequest", jsonStringRequest).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().json(jsonStringResponse));

	}

	@Test
	void testGetProcessDetail_PackagingAndDeliverChargeServiceException() throws Exception {
		ProcessRequest processRequestObject = processRequest("INTEGRAL ITEM");

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(processRequestObject);

		when(processInitiator.processReturnOrder(processRequestObject))
				.thenThrow(PackagingAndDeliverChargeServiceException.class);
		this.mockMvc
				.perform(get("/ProcessDetail").header("Authorization", "test token")
						.param("processRequest", jsonStringRequest).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	void testGetProcessDetail_ComponentNotIdentifiedException() throws Exception {
		ProcessRequest processRequestObject = processRequest("INTEGRAL ITEM");

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(processRequestObject);

		when(processInitiator.processReturnOrder(processRequestObject))
				.thenThrow(ComponentNotIdentifiedException.class);
		this.mockMvc
				.perform(get("/ProcessDetail").header("Authorization", "test token")
						.param("processRequest", jsonStringRequest).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	void testGetProcessDetail_ProcessingFailedException() throws Exception {
		ProcessRequest processRequestObject = processRequest("INTEGRAL ITEM");

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(processRequestObject);

		when(processInitiator.processReturnOrder(processRequestObject)).thenThrow(ProcessingFailedException.class);
		this.mockMvc
				.perform(get("/ProcessDetail").header("Authorization", "test token")
						.param("processRequest", jsonStringRequest).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isUnprocessableEntity());

	}

	@Test
	void testGetProcessDetail_Validationfailed() throws Exception {
		ProcessRequest processRequestObject = processRequest("INTEGRAL ITEM");

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(processRequestObject);

		when(processInitiator.validateProcessRequestObject(any())).thenThrow(ValidationFailedException.class);
		this.mockMvc
				.perform(get("/ProcessDetail").header("Authorization", "test token")
						.param("processRequest", jsonStringRequest).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());

	}

	@Test
	void testSaveConfirmedOrder() throws Exception {
		ConfirmReturnRequest confirmReturnBody = getConfirmReturn();

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(confirmReturnBody);

		when(processCompletionService.completeProcess(any())).thenReturn("success");
		this.mockMvc
				.perform(post("/CompleteProcessing").contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string("success"));

	}

	@Test
	void testSaveConfirmedOrderFailed() throws Exception {
		ConfirmReturnRequest confirmReturnBody = getConfirmReturn();

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(confirmReturnBody);

		when(processCompletionService.completeProcess(any())).thenThrow(ConfirmationFailedException.class);
		this.mockMvc
				.perform(post("/CompleteProcessing").contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andDo(print()).andExpect(status().isExpectationFailed());

	}

	@Test
	void testSaveConfirmedOrderBadRequestForInvalidCreditCard() throws Exception {
		ConfirmReturnRequest confirmReturnBody = getInvalidCreditCardNumber();

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(confirmReturnBody);

		this.mockMvc
				.perform(post("/CompleteProcessing").contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("invalid credit card number"));

	}

	@Test
	void testSaveConfirmedOrderBadRequest() throws Exception {
		ConfirmReturnRequest confirmReturnBody = getInvalidConfirmReturn();

		ObjectMapper map = new ObjectMapper();
		String jsonStringRequest = map.writeValueAsString(confirmReturnBody);

		this.mockMvc
				.perform(post("/CompleteProcessing").contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("invalid payment details for confirming return order"));

	}

	ConfirmReturnRequest getConfirmReturn() {
		return new ConfirmReturnRequest(200L, "1234567812345670", 8000L, 800L);
	}

	ConfirmReturnRequest getInvalidCreditCardNumber() {
		return new ConfirmReturnRequest(200L, "12345678", 8000L, 800L);
	}

	ConfirmReturnRequest getInvalidConfirmReturn() {
		return new ConfirmReturnRequest(200L, "1234567812345670", 8000L, null);
	}
}
