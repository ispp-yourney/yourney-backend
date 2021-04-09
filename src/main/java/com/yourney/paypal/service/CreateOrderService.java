package com.yourney.paypal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.Payee;
import com.paypal.orders.PaymentInstruction;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderService {
    
    @Autowired
    private PayPalHttpClient paypalClient;

	@Value("${paypal.callback-url}")
	private String paypalCallbackUrl;


	private OrderRequest buildSubscriptionRequestBody(String id) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");

		System.out.println(paypalCallbackUrl);
		
		ApplicationContext applicationContext = new ApplicationContext().brandName("YOURNEY").landingPage("BILLING")
				.cancelUrl(paypalCallbackUrl + "/paypal/cancel").returnUrl(paypalCallbackUrl + "/paypal/capture").userAction("CONTINUE");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId(id)
				.description("Subscripción de usuario de 30 días").softDescriptor("SUBSCRIPTION")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("EUR").value("2.00")
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("EUR").value("2.00"))))
				.items(List.of(
					new Item().name("Subscripción").description("Subscripción de usuario de 30 días")
						.unitAmount(new Money().currencyCode("EUR").value("2.00"))
						.quantity("1").sku("SUB-30")
				));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}

	private OrderRequest buildSponsorshipRequestBody(String id) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");

		System.out.println(paypalCallbackUrl);
		
		ApplicationContext applicationContext = new ApplicationContext().brandName("YOURNEY").landingPage("BILLING")
				.cancelUrl(paypalCallbackUrl + "/paypal/cancel").returnUrl(paypalCallbackUrl + "/paypal/capture").userAction("CONTINUE");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId(id)
				.description("Patrocinio de un punto de interés por 30 días").softDescriptor("SPONSORSHIP")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("EUR").value("200.00")
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("EUR").value("200.00"))))
				.items(List.of(
					new Item().name("Patrocinio").description("Patrocinio de un punto de interés por 30 días")
						.unitAmount(new Money().currencyCode("EUR").value("200.00"))
						.quantity("1").sku("SPO-30")
				));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}


	public HttpResponse<Order> createOrder(String orderType, String id) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");

		switch (orderType) {
			case "SUBSCRIPTION":
				request.requestBody(buildSubscriptionRequestBody(id));
				break;
			
			case "SPONSORSHIP":
				request.requestBody(buildSponsorshipRequestBody(id));
				break;

			default:
				break;
		}

		HttpResponse<Order> response = paypalClient.execute(request);
		return response;
	}

}
