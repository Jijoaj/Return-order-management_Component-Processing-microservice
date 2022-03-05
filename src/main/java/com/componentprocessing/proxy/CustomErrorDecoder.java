package com.componentprocessing.proxy;

import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:

			return new PackagingAndDeliverChargeServiceException("bad request to Packaging and Delivery service");
		case 401:
			return new PackagingAndDeliverChargeServiceException(
					"request not authorized to get service from Packaging and Delivery service");

		case 404:
			return new PackagingAndDeliverChargeServiceException(
					"component not identified by Packaging and Delivery service");
		default:
			return new PackagingAndDeliverChargeServiceException(
					"unable to fetch data from Packaging and deliver charge service for this component");
		}
	}

}
