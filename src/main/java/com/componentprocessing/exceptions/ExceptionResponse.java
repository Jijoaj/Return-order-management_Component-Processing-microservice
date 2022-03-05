package com.componentprocessing.exceptions;

import java.util.Date;

import lombok.Getter;

//@Data
@Getter
public class ExceptionResponse {
	private Date timeStamp;
	private String message;
	private Object processRequest;

	public ExceptionResponse(Date timeStamp, String message, Object processRequest) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.processRequest = processRequest;
	}

}
