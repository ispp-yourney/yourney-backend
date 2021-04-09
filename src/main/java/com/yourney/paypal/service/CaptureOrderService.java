package com.yourney.paypal.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import com.paypal.orders.*;
import com.yourney.paypal.exception.CheckoutException;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;

@Service
public class CaptureOrderService {

    @Autowired
    private PayPalHttpClient paypalClient;

	@Autowired
	private UserService userService;
	
	public OrderRequest buildRequestBody() {
		return new OrderRequest();
	}

	public HttpResponse<Order> getOrder(String orderId) throws IOException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		return paypalClient.execute(request);
	}


	public String captureOrder(String orderId) throws IOException, CheckoutException {
		OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
		request.requestBody(buildRequestBody());
		HttpResponse<Order> response = paypalClient.execute(request);

		response = getOrder(response.result().id());

		if (response.statusCode() == 200) {
			String sku = response.result().purchaseUnits().get(0).items().get(0).sku();
			String id = response.result().purchaseUnits().get(0).referenceId();

			if (sku.contains("SUB-")) {
				Optional<User> foundUser = userService.getOneById(Long.parseLong(id));

				if (!foundUser.isPresent()) {
					throw new CheckoutException();
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

				if (updatedUser != null) {
					return "/perfil/" + user.getUsername();
				}

			} /* else if (sku.contains("SPO-")) {
				
			} */
		}

		throw new CheckoutException();
	}

	
}
