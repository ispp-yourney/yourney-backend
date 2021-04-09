package com.yourney.paypal.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfiguration {
    
	@Value("${paypal.client.app}")
	private String clientId;

	@Value("${paypal.client.secret}")
	private String clientSecret;
	

	@Bean
	public PayPalEnvironment paypalEnviroment() {
		return new PayPalEnvironment.Sandbox(clientId, clientSecret);
	}

	@Bean
	public PayPalHttpClient paypalClient() {
		return new PayPalHttpClient(paypalEnviroment());
    }

}
