package com.componentprocessing.services;

import com.componentprocessing.model.ProcessResponse;

public interface ProcessService {

	ProcessResponse fetchReturnOrderdeatils(Long packagingAndDeliveryCharge, Integer quantity);

}
