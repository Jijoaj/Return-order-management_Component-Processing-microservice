package com.componentprocessing.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.componentprocessing.model.ConfirmReturn;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
class ConfirmReturnRepositoryTest {

	@Autowired
	ConfirmReturnRepository confirmReturnRepository;
	ConfirmReturn confirmReturndetails;

	@BeforeEach
	void setupTest() {
		confirmReturndetails = getConfirmReturn();
	}

	@Test
	void testsaveReturnConfirmation() {

		ConfirmReturn savedReturnConfirmation = confirmReturnRepository.saveReturnConfirmation(confirmReturndetails);
		assertEquals(confirmReturndetails, savedReturnConfirmation);

	}

	ConfirmReturn getConfirmReturn() {
		return new ConfirmReturn(200L, "1234567812345670", 8000L, 800L);
	}
}
