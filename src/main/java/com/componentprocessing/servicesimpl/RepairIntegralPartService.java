package com.componentprocessing.servicesimpl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.services.ProcessService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RepairIntegralPartService implements ProcessService {

	private static final int DEFAULT_PROCESSING_DURATION_INTEGRAL_ITEM = 5;
	private static final long DEFAULT_PROCESSING_CHARGE_INTEGRAL_ITEM = 500L;

	@Override
	public ProcessResponse fetchReturnOrderdeatils(Long packagingAndDeliveryCharge, Integer quantity) {
		Date returnDate = getReturnDate(quantity);
		long totalProcessingCharge = DEFAULT_PROCESSING_CHARGE_INTEGRAL_ITEM * quantity;
		ProcessResponse processResponse = new ProcessResponse(totalProcessingCharge, packagingAndDeliveryCharge,
				returnDate);
		log.info(
				"Processing completed with processing charge :{} packaging and delivery charge :{} and return date :{}",
				totalProcessingCharge, packagingAndDeliveryCharge, returnDate);
		return processResponse;
	}

	private static Date getReturnDate(int quantity) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, DEFAULT_PROCESSING_DURATION_INTEGRAL_ITEM * quantity);
		return c.getTime();
	}

}
