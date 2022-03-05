package com.componentprocessing.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.componentprocessing.model.PackagingAndDeliveryChargeResponse;

@FeignClient(url = "${packagingAndDeliveryServiceURL}", name = "packaginganddelivery", configuration = {
		FeignConfig.class })
public interface PackagingAndDeliveryProxy {

	@GetMapping("/GetPackagingDeliveryCharge")
	PackagingAndDeliveryChargeResponse retrievePackagingAndDeliverCharge(@RequestParam String componentType,
			@RequestParam Integer count);

}
