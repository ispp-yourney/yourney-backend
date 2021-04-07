package com.yourney.paypal.service;

import java.io.IOException;

import com.paypal.orders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;

@Service
public class CaptureOrderService {

    @Autowired
    private PayPalHttpClient paypalClient;
	
	public OrderRequest buildRequestBody() {
		return new OrderRequest();
	}

	public HttpResponse<Order> captureOrder(String orderId, boolean debug) throws IOException {
		OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
		request.requestBody(buildRequestBody());
		HttpResponse<Order> response = paypalClient.execute(request);
		if (debug) {
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Order ID: " + response.result().id());
			System.out.println("Links: ");
			for (LinkDescription link : response.result().links()) {
				System.out.println("\t" + link.rel() + ": " + link.href());
			}
			System.out.println("Capture ids:");
			for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
				for (Capture capture : purchaseUnit.payments().captures()) {
					System.out.println("\t" + capture.id());
				}
			}
			System.out.println("Buyer: ");
			Payer buyer = response.result().payer();
			System.out.println("\tEmail Address: " + buyer.email());
			System.out.println("\tName: " + buyer.name().givenName() + " " + buyer.name().surname());
		}
		return response;
	}

	
}
