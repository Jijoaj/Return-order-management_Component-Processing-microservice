package com.componentprocessing.servicesimpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.dao.ConfirmReturnRepo;
import com.componentprocessing.dao.UserRequestsRepository;
import com.componentprocessing.model.ConfirmReturn;
import com.componentprocessing.model.UserRequests;
import com.componentprocessing.services.OrderDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	UserRequestsRepository userRequestsRepository;

	@Autowired
	ConfirmReturnRepo confirmReturnRepo;

	@Override
	public List<ConfirmReturn> findOrderDetails(String name) {
		log.info("Fetching details of the requests the user  {} has raised", name);
		List<UserRequests> requestList = userRequestsRepository.findAllByName(name);
		if (requestList.stream().count() == 0) {
			log.info("user {} has not raised any orders yet", name);
			return Collections.emptyList();

		}
		requestList.stream().map(UserRequests::getRequestId).forEach(x -> log.info("request id : {}", x));
		log.info("Successfully fetched request id's for the user");
		log.info("fetching order details using the request ids");
		return requestList.stream().map(UserRequests::getRequestId).map(x -> confirmReturnRepo.findById(x))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

}
