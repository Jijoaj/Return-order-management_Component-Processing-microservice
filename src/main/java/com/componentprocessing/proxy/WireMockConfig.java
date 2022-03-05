package com.componentprocessing.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.tomakehurst.wiremock.WireMockServer;

@Profile("staging")
@Configuration
public class WireMockConfig {

	@Autowired
	private WireMockServer wireMockServer;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public WireMockServer mockBooksService() {
		return new WireMockServer(9561);
	}

}
