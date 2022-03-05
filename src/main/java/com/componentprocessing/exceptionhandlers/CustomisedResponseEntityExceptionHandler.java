package com.componentprocessing.exceptionhandlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.componentprocessing.exceptions.ComponentNotIdentifiedException;
import com.componentprocessing.exceptions.ConfirmationFailedException;
import com.componentprocessing.exceptions.ExceptionResponse;
import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;
import com.componentprocessing.exceptions.ProcessingFailedException;
import com.componentprocessing.exceptions.ValidationFailedException;

@ControllerAdvice
@RestController
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String INVALID_PAYMENT_DETAILS_FOR_CONFIRMING_RETURN_ORDER = "invalid payment details for confirming return order";
	private static final String INVALID_CREDIT_CARD_NUMBER = "invalid credit card number";
	private static final String UNABLE_TO_PROCESS_REQUEST = "unable to process request";
	@Autowired
	RequestContext requestContext;

	@ExceptionHandler(PackagingAndDeliverChargeServiceException.class)
	public final ResponseEntity<ExceptionResponse> handlePackagingAndDeliverChargeServiceException(
			PackagingAndDeliverChargeServiceException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				requestContext.getRequestBody());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ComponentNotIdentifiedException.class)
	public final ResponseEntity<ExceptionResponse> handleComponentNotIdentifiedException(
			ComponentNotIdentifiedException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				requestContext.getRequestBody());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProcessingFailedException.class)
	public final ResponseEntity<ExceptionResponse> handleProcessingFailedException(ProcessingFailedException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), UNABLE_TO_PROCESS_REQUEST,
				requestContext.getRequestBody());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ConfirmationFailedException.class)
	public final ResponseEntity<ExceptionResponse> handleConfirmationFailedException(ConfirmationFailedException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), UNABLE_TO_PROCESS_REQUEST,
				requestContext.getRequestBody());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(ValidationFailedException.class)
	public final ResponseEntity<ExceptionResponse> handleValidationFailedException(ValidationFailedException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				requestContext.getRequestBody());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (ex.getMessage().contains(INVALID_CREDIT_CARD_NUMBER)) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), INVALID_CREDIT_CARD_NUMBER,
					request.toString());
			return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
		}
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				INVALID_PAYMENT_DETAILS_FOR_CONFIRMING_RETURN_ORDER, request.toString());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
