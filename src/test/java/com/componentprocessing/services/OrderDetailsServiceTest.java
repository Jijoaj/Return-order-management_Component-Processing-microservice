package com.componentprocessing.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.componentprocessing.dao.ConfirmReturnRepo;
import com.componentprocessing.dao.UserRequestsRepository;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
public class OrderDetailsServiceTest {

	@Autowired
	OrderDetailsService orderDetailsService;

	@MockBean
	ConfirmReturnRepo confirmReturnRepo;

	@MockBean
	UserRequestsRepository userRequestsRepository;

	@Test
	void testOrderDetailsService() {

	}

}
