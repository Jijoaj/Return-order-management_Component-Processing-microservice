package com.componentprocessing.servicesimpl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.services.ProcessService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReplacementForAccessoryService implements ProcessService {

	private static final int DEFAULT_PROCESSING_DURATION_ACCESSORY = 2;
	private static final long DEFAULT_PROCESSING_CHARGE_ACCESSORY = 300L;

	@Override
	public ProcessResponse fetchReturnOrderdeatils(Long packagingAndDeliveryCharge, Integer quantity) {
		Date returnDate = getReturnDate(quantity);
		long totalProcessingCharge = DEFAULT_PROCESSING_CHARGE_ACCESSORY * quantity;
		ProcessResponse processResponse = new ProcessResponse(totalProcessingCharge, packagingAndDeliveryCharge,
				returnDate);
		log.info(
				"Processing completed with processing charge :{} packaging and delivery charge :{} and return date :{}",
				totalProcessingCharge, packagingAndDeliveryCharge, returnDate);
		return processResponse;

	}

	private static Date getReturnDate(int quantity) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, DEFAULT_PROCESSING_DURATION_ACCESSORY * quantity);
		return c.getTime();
	}

}
