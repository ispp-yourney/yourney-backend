package com.yourney.paypal.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import com.paypal.orders.*;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;

@Service
public class CaptureOrderService {

    @Autowired
    private PayPalHttpClient paypalClient;

	@Autowired
	private UserService userService;

	@Value("${paypal.frontend-url}")
    private String frontendUrl;
	
	public OrderRequest buildRequestBody() {
		return new OrderRequest();
	}

	public HttpResponse<Order> getOrder(String orderId) throws IOException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = paypalClient.execute(request);
		return response;
	}


	public String captureOrder(String orderId) throws IOException {
		OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
		request.requestBody(buildRequestBody());
		HttpResponse<Order> response = paypalClient.execute(request);

		System.out.println("Order ID: " + response.result().id());

		response = getOrder(response.result().id());

		if (response.statusCode() == 200) {
			String sku = response.result().purchaseUnits().get(0).items().get(0).sku();
			String id = response.result().purchaseUnits().get(0).referenceId();

			if (sku.contains("SUB-")) {
				Optional<User> foundUser = userService.getOneById(Long.parseLong(id));

				if (!foundUser.isPresent()) {
					return "/error";
				}
		
				int subscriptionDays = Integer.parseInt(sku.split("-")[1]);
		
				User user = foundUser.get();
				if(user.getExpirationDate()!=null && user.getExpirationDate().isAfter(LocalDateTime.now())){
					user.setExpirationDate(user.getExpirationDate().plusDays(subscriptionDays));
					user.setPlan(1);
				} else {
					user.setExpirationDate(LocalDateTime.now().plusDays(subscriptionDays));
					user.setPlan(1);
				}
				
				User updatedUser = userService.save(user);

				if (updatedUser == null) {
					return "/error";
				} else {
					return "/perfil/" + user.getUsername();
				}
			} else if (sku.contains("SPO-")) {
				
			}
		}

		return "/error";
	}

	
}
