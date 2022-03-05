package com.componentprocessing.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RestResource
@RequiredArgsConstructor
@Getter
public class ConfirmReturn {
	@Id
	@NotNull
	@Column(name = "requestId")
	private long requestIdToSave;
	@NotNull
	@CreditCardNumber
	@Size(min = 16, max = 16)
	@Column(name = "creditCardNumber")
	private String creditCardNumberToSave;
	@NotNull
	@Min(value = 0)
	@Column(name = "creditLimit")
	private Long creditLimitToSave;
	@NotNull
	@Column(name = "processingCharge")
	private Long processingChargeToSave;

	public ConfirmReturn(long requestId, String creditCardNumber, Long creditLimit, Long processingCharge) {
		this.requestIdToSave = requestId;
		this.creditCardNumberToSave = creditCardNumber;
		this.creditLimitToSave = creditLimit;
		this.processingChargeToSave = processingCharge;
	}

	public ConfirmReturn(ConfirmReturnRequest confirmReturnRequest) {

		this.requestIdToSave = confirmReturnRequest.getRequestId();
		this.creditCardNumberToSave = confirmReturnRequest.getCreditCardNumber();
		this.creditLimitToSave = confirmReturnRequest.getCreditLimit();
		this.processingChargeToSave = confirmReturnRequest.getProcessingCharge();
	}

	@Override
	public int hashCode() {
		return Objects.hash(creditCardNumberToSave, creditLimitToSave, processingChargeToSave, requestIdToSave);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfirmReturn other = (ConfirmReturn) obj;
		return Objects.equals(creditCardNumberToSave, other.creditCardNumberToSave)
				&& Objects.equals(creditLimitToSave, other.creditLimitToSave)
				&& Objects.equals(processingChargeToSave, other.processingChargeToSave)
				&& requestIdToSave == other.requestIdToSave;
	}

}
