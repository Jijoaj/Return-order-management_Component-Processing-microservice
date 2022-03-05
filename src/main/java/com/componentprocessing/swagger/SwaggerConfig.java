package com.componentprocessing.swagger;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final String DEFAULT_CONTACT = "jijo.aj@cognizant.com";
	@SuppressWarnings("deprecation")
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("ComponentProcessing Microservice",
			"The intent of this Microservice is to determine the Component processing detail. It interacts with packaging and delivery microservice to get the consolidated cost for the processing. "
					+ "Post Authorization using JWT, based upon the type of component – Integral or Accessory, repair or "
					+ "replacement workflow respectively, should determine the processing details.",
			"1.0", "Only Authorized requests can access these REST End Points", DEFAULT_CONTACT,
			"Return order Management", "http://localhost/returnordermanagement");
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>();

	static {
		DEFAULT_PRODUCES_AND_CONSUMES.add("application/json");
		DEFAULT_PRODUCES_AND_CONSUMES.add("application/xml");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}
