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
import com.componentprocessing.servicesimpl.RepairIntegralPartService;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = { RepairIntegralPartService.class })
class RepairIntegralPartServiceTest {

	static ProcessService processService;
	private ProcessResponse fetchReturnOrderdetails;
	private static String currentDate;
	static Integer quantity;

	@BeforeAll
	static void setupbeforeAllTest() {
		processService = new RepairIntegralPartService();
		quantity = 5;

	}

	@BeforeEach
	void setupBeforeEachTest() {
		Date currentDateandTime = getReturnDate(quantity);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		currentDate = dateFormat.format(currentDateandTime);
	}

	@Test
	void testRepairIntegralPartService() {
		Long packagingAndDeliveryCharge = 500L;
		fetchReturnOrderdetails = processService.fetchReturnOrderdeatils(packagingAndDeliveryCharge, quantity);
		assertEquals(packagingAndDeliveryCharge, fetchReturnOrderdetails.getPackagingAndDeliveryCharge());
		assertEquals(2500L, fetchReturnOrderdetails.getProcessingCharge());
		assertEquals(currentDate, fetchReturnOrderdetails.getDateOfDelivery());
	}

	static Date getReturnDate(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 5 * n);
		return c.getTime();
	}

}
