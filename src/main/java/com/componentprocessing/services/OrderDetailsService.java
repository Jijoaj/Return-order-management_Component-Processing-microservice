package com.componentprocessing.services;

import java.util.List;

import com.componentprocessing.model.ConfirmReturn;

public interface OrderDetailsService {
	List<ConfirmReturn> findOrderDetails(String name);

}
