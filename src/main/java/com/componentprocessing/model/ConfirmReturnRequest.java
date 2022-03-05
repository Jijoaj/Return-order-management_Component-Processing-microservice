package com.componentprocessing.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Getter;

@Getter
public class ConfirmReturnRequest {

	@NotNull
	private long requestId;
	@NotNull
	@CreditCardNumber(message = "invalid credit card number")
	@Size(min = 16, max = 16, message = "invalid credit card number length")
	private String creditCardNumber;
	@NotNull
	@Min(value = 0)
	private Long creditLimit;
	@NotNull
	private Long processingCharge;

	public ConfirmReturnRequest(@NotNull long requestId, @NotNull @CreditCardNumber String creditCardNumber,
			@NotNull @Min(0) Long creditLimit, @NotNull Long processingCharge) {
		super();
		this.requestId = requestId;
		this.creditCardNumber = creditCardNumber;
		this.creditLimit = creditLimit;
		this.processingCharge = processingCharge;
	}

}
