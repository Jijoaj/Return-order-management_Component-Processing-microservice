package com.componentprocessing.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.componentprocessing.model.UserRequests;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@ActiveProfiles("test")
class UserRequestRepositoryTest {

	@Autowired
	UserRequestRepo userRequestRepo;

	@Autowired
	UserRequestsRepository userRequestsRepository;

	UserRequests userRequests;

	@BeforeEach
	void setup() {
		userRequests = new UserRequests("jijo", 10000L);
	}

	@Test
	@DirtiesContext
	void testSaveUserRequest() {

		UserRequests saveUserRequest = userRequestRepo.saveUserRequest(userRequests);
		assertEquals(userRequests, saveUserRequest);
	}

	@Test
	@DirtiesContext
	void testFindAllRequests() {
		userRequestRepo.saveUserRequest(userRequests);
		userRequests.setRequestId(2000L);
		userRequestRepo.saveUserRequest(userRequests);
		List<UserRequests> findAllByName = userRequestsRepository.findAllByName(userRequests.getName());
		findAllByName.stream().forEach(x -> assertEquals(userRequests.getName(), x.getName()));
		assertEquals(2, findAllByName.stream().count());
	}

}
