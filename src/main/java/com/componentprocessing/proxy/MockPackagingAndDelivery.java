package com.componentprocessing.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@Service
@Profile("staging")
public class MockPackagingAndDelivery {

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String PACKAGING_AND_DELIVERY_CHARGE_200 = "{ \"packagingAndDeliveryCharge\":200}";

	public void setupMockPackagingAndDeliveryResponse(WireMockServer mockService) {
		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/GetPackagingDeliveryCharge?componentType=accessory&count=5"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withBody(PACKAGING_AND_DELIVERY_CHARGE_200)));
		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/GetPackagingDeliveryCharge?componentType=accessory2&count=5"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.UNAUTHORIZED.value())
								.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withBody(PACKAGING_AND_DELIVERY_CHARGE_200)));
		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/GetPackagingDeliveryCharge?componentType=accessory3&count=5"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.BAD_REQUEST.value())
								.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withBody(PACKAGING_AND_DELIVERY_CHARGE_200)));
		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/GetPackagingDeliveryCharge?componentType=accessory4&count=5"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.NOT_FOUND.value())
								.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withBody(PACKAGING_AND_DELIVERY_CHARGE_200)));
		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/GetPackagingDeliveryCharge?componentType=accessory5&count=5"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.BAD_GATEWAY.value())
								.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withBody(PACKAGING_AND_DELIVERY_CHARGE_200)));
	}

}
