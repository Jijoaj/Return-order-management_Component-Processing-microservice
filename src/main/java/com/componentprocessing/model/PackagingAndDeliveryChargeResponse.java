package com.componentprocessing.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PackagingAndDeliveryChargeResponse {

	@ApiModelProperty(value = "Packaging and delivery charge for the given component")
	@NotNull
	Long packagingAndDeliveryCharge;

	public PackagingAndDeliveryChargeResponse(Long packagingAndDeliveryCharge) {
		super();
		this.packagingAndDeliveryCharge = packagingAndDeliveryCharge;
	}

	public PackagingAndDeliveryChargeResponse() {
		super();
	}

}
