package com.componentprocessing.services;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.componentprocessing.exceptions.PackagingAndDeliverChargeServiceException;
import com.componentprocessing.model.PackagingAndDeliveryChargeResponse;
import com.componentprocessing.proxy.MockPackagingAndDelivery;
import com.componentprocessing.proxy.PackagingAndDeliveryProxy;
import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootTest
@ActiveProfiles("staging")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class PackagingAndDeliveryServiceTest {

	@Autowired
	PackagingAndDeliveryService packagingAndDeliveryService;
	@Autowired
	PackagingAndDeliveryProxy packagingAndDeliveryProxy;

	@Autowired
	private WireMockServer mockPackagingAndDeliveryService;
	@Autowired
	MockPackagingAndDelivery mockPackagingAndDelivery;

	PackagingAndDeliveryChargeResponse packagingAndDeliverCharge;

	@BeforeAll
	void init() {
		packagingAndDeliverCharge = getPackagingAndDeliverCharge();
		mockPackagingAndDelivery.setupMockPackagingAndDeliveryResponse(mockPackagingAndDeliveryService);

	}

	@BeforeEach
	void setupEach() {
		mockPackagingAndDeliveryService.start();
	}

	@Test
	void testgetPackagingAndDeliveryCharge() {

		long extractedPackagingAndDeliveryCharge = packagingAndDeliveryService
				.getPackagingAndDeliveryCharge("accessory", 5);

		assertEquals(packagingAndDeliverCharge.getPackagingAndDeliveryCharge(), extractedPackagingAndDeliveryCharge);
	}

	@Test
	void testgetPackagingAndDeliveryChargeNotAuthorized() {

		assertThatExceptionOfType(PackagingAndDeliverChargeServiceException.class)
				.isThrownBy(() -> packagingAndDeliveryService.getPackagingAndDeliveryCharge("accessory2", 5));

	}

	@Test
	void testgetPackagingAndDeliveryChargeBadRequest() {

		assertThatExceptionOfType(PackagingAndDeliverChargeServiceException.class)
				.isThrownBy(() -> packagingAndDeliveryService.getPackagingAndDeliveryCharge("accessory3", 5));

	}

	@Test
	void testgetPackagingAndDeliveryChargeNotFound() {

		assertThatExceptionOfType(PackagingAndDeliverChargeServiceException.class)
				.isThrownBy(() -> packagingAndDeliveryService.getPackagingAndDeliveryCharge("accessory4", 5));

	}

	@Test
	void testgetPackagingAndDeliveryChargegeneralException() {

		assertThatExceptionOfType(PackagingAndDeliverChargeServiceException.class)
				.isThrownBy(() -> packagingAndDeliveryService.getPackagingAndDeliveryCharge("accessory5", 5));

	}

	@AfterEach
	void tearDown() {
		mockPackagingAndDeliveryService.stop();
	}

	@AfterAll
	void shutdown() {
		mockPackagingAndDeliveryService.shutdown();
	}

	PackagingAndDeliveryChargeResponse getPackagingAndDeliverCharge() {
		return new PackagingAndDeliveryChargeResponse(200L);
	}

}
