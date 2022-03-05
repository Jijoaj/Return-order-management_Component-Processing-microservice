package com.componentprocessing.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.componentprocessing.dao.ConfirmReturnRepository;
import com.componentprocessing.exceptions.ConfirmationFailedException;
import com.componentprocessing.model.ConfirmReturn;
import com.componentprocessing.model.ConfirmReturnRequest;

@SpringBootTest
class ProcessCompletionServiceTest {
	@MockBean
	ConfirmReturnRepository confirmReturnRepository;

	@Autowired
	ProcessCompletionService processCompletionService;

	private ConfirmReturnRequest confirmReturnRequest;

	private String status;

	@BeforeEach
	void setup() {
		confirmReturnRequest = getConfirmReturnRequest();
	}

	@Test
	void testProcessCompletionServiceSuccess() {
		ConfirmReturn confirmReturnObject = new ConfirmReturn(confirmReturnRequest);
		when(confirmReturnRepository.saveReturnConfirmation(confirmReturnObject)).thenReturn(confirmReturnObject);
		status = processCompletionService.completeProcess(confirmReturnRequest);
		assertEquals("success", status);

	}

	@Test
	void testProcessCompletionFailed() {

		when(confirmReturnRepository.saveReturnConfirmation(any())).thenReturn(null);
		assertThatExceptionOfType(ConfirmationFailedException.class)
				.isThrownBy(() -> processCompletionService.completeProcess(confirmReturnRequest));

	}

	@Test
	void testProcessCompletionException() {
		when(confirmReturnRepository.saveReturnConfirmation(any())).thenThrow(RuntimeException.class);
		assertThatExceptionOfType(ConfirmationFailedException.class)
				.isThrownBy(() -> processCompletionService.completeProcess(confirmReturnRequest));
	}

	ConfirmReturnRequest getConfirmReturnRequest() {
		return new ConfirmReturnRequest(200L, "12345678903555", 8000L, 800L);
	}
}
