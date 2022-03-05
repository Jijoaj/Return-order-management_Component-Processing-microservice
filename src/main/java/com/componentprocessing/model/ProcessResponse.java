package com.componentprocessing.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RestResource
@Getter
@RequiredArgsConstructor
public class ProcessResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response")
	@SequenceGenerator(initialValue = 10000, name = "response")
	private Long requestId;

	private long processingCharge;

	private long packagingAndDeliveryCharge;

	private String dateOfDelivery;

	public ProcessResponse(Long processingCharge, Long packagingAndDeliveryCharge, Date dateOfDelivery) {
		this.processingCharge = processingCharge;
		this.packagingAndDeliveryCharge = packagingAndDeliveryCharge;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.dateOfDelivery = dateFormat.format(dateOfDelivery);
	}

	public ProcessResponse(Long requestId, long processingCharge, long packagingAndDeliveryCharge,
			Date dateOfDelivery) {
		this.requestId = requestId;
		this.processingCharge = processingCharge;
		this.packagingAndDeliveryCharge = packagingAndDeliveryCharge;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.dateOfDelivery = dateFormat.format(dateOfDelivery);
	}

	public ProcessResponse(Long requestId, long processingCharge, long packagingAndDeliveryCharge,
			String dateOfDelivery) {
		this.requestId = requestId;
		this.processingCharge = processingCharge;
		this.packagingAndDeliveryCharge = packagingAndDeliveryCharge;
		this.dateOfDelivery = dateOfDelivery;
	}

}
