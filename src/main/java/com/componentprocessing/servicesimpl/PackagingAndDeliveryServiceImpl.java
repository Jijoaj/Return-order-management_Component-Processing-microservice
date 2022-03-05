package com.componentprocessing.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;
import com.componentprocessing.proxy.PackagingAndDeliveryProxy;
import com.componentprocessing.services.PackagingAndDeliveryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PackagingAndDeliveryServiceImpl implements PackagingAndDeliveryService {

	@Autowired
	PackagingAndDeliveryProxy packagingAndDeliveryProxy;

	@Override
	public long getPackagingAndDeliveryCharge(String componentType, Integer count) {
		try {
			log.info("sending request to Packaging and Delivery service with componenttype :{} and count :{}",
					componentType, count);
			return packagingAndDeliveryProxy.retrievePackagingAndDeliverCharge(componentType, count)
					.getPackagingAndDeliveryCharge();

		} catch (Exception e) {
			log.warn(e.getMessage());
			throw new PackagingAndDeliverChargeServiceException(e.getMessage());
		}
	}

}
