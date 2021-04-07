package com.yourney.paypal.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

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
