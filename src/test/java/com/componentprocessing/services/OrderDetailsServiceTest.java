package com.componentprocessing.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.componentprocessing.dao.ConfirmReturnRepo;
import com.componentprocessing.dao.UserRequestsRepository;
import com.componentprocessing.model.ConfirmReturn;
import com.componentprocessing.model.UserRequests;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
public class OrderDetailsServiceTest {

	@Autowired
	OrderDetailsService orderDetailsService;

	@MockBean
	ConfirmReturnRepo confirmReturnRepo;

	@MockBean
	UserRequestsRepository userRequestsRepository;
	UserRequests userRequests;

	@BeforeEach
	void setup() {
		userRequests = new UserRequests("jijo", 10000L);
	}

	Optional<ConfirmReturn> getConfirmReturnRequest() {
		return Optional.of(new ConfirmReturn(200L, "12345678903555", 8000L, 800L));
	}

	@Test
	void testOrderDetailsService_ValidName() {
		when(userRequestsRepository.findAllByName(userRequests.getName())).thenReturn(Arrays.asList(userRequests));
		when(confirmReturnRepo.findById(userRequests.getRequestId())).thenReturn(getConfirmReturnRequest());
		List<ConfirmReturn> orderDetails = orderDetailsService.findOrderDetails(userRequests.getName());
		assertEquals(orderDetails, Arrays.asList(getConfirmReturnRequest().get()));
	}

	@Test
	void testOrderDetailsService_InvalidName() {
		when(userRequestsRepository.findAllByName(userRequests.getName())).thenReturn(Collections.emptyList());
		List<ConfirmReturn> orderDetails = orderDetailsService.findOrderDetails(userRequests.getName());
		assertEquals(orderDetails, Collections.emptyList());
	}

}
