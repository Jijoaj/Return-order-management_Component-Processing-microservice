package com.componentprocessing.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.componentprocessing.model.ProcessResponse;
import com.componentprocessing.servicesimpl.ReplacementForAccessoryService;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = { ReplacementForAccessoryService.class })
class ReplacementForAccessoryServiceTest {

	private static final int _2 = 2;
	static ProcessService processService;
	private ProcessResponse fetchReturnOrderdeatils;
	private static String currentDate;

	@BeforeAll
	static void setupbeforeAllTest() {
		processService = new ReplacementForAccessoryService();

	}

	@BeforeEach
	void setupBeforeEachTest() {
		Date currentDateandTime = getReturnDate(_2);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		currentDate = dateFormat.format(currentDateandTime);
	}

	@Test
	void testRepairIntegralPartService() {
		Long packagingAndDeliveryCharge = 300L;
		fetchReturnOrderdeatils = processService.fetchReturnOrderdeatils(packagingAndDeliveryCharge, _2);
		assertEquals(packagingAndDeliveryCharge, fetchReturnOrderdeatils.getPackagingAndDeliveryCharge());
		assertEquals(600L, fetchReturnOrderdeatils.getProcessingCharge());
		assertEquals(currentDate, fetchReturnOrderdeatils.getDateOfDelivery());
	}

	static Date getReturnDate(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 2 * n);
		return c.getTime();
	}

}
