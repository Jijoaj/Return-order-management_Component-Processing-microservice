package com.componentprocessing.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "ComponentProcessingService";

	@Value("${publicKey}")
	private String publicKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().mvcMatchers(HttpMethod.GET, "/ProcessDetail").hasAnyRole("USER", "ADMIN")
				.mvcMatchers(HttpMethod.POST, "/CompleteProcessing").hasAnyRole("USER", "ADMIN")
				.mvcMatchers(HttpMethod.GET, "/processResponse", "/confirmReturn").permitAll()
				.mvcMatchers("/actuator/*", "/v2/api-docs*", "/processResponse/*", "/confirmReturn/*", "/h2-console/",
						"/h2-console/*")
				.permitAll().anyRequest().denyAll().and().csrf().disable();

	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setVerifierKey(publicKey);
		return jwtAccessTokenConverter;

	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
}
