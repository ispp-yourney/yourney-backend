package com.yourney.paypal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.Money;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.yourney.paypal.exception.CheckoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderService {
    
    @Autowired
    private PayPalHttpClient paypalClient;

	@Value("${paypal.callback-url}")
	private String paypalCallbackUrl;

	private final String SUBSCRIPTION_PRICE = "2.00";
	private final String SPONSORSHIP_PRICE = "200.00";

	private OrderRequest buildSubscriptionRequestBody(String id) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");
		
		ApplicationContext applicationContext = new ApplicationContext().brandName("YOURNEY").landingPage("BILLING")
				.cancelUrl(paypalCallbackUrl + "/paypal/cancel").returnUrl(paypalCallbackUrl + "/paypal/capture").userAction("CONTINUE");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId(id)
				.description("Subscripción de usuario de 30 días").softDescriptor("SUBSCRIPTION")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("EUR").value(SUBSCRIPTION_PRICE)
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("EUR").value(SUBSCRIPTION_PRICE))))
				.items(List.of(
					new Item().name("Subscripción").description("Subscripción de usuario de 30 días")
						.unitAmount(new Money().currencyCode("EUR").value(SUBSCRIPTION_PRICE))
						.quantity("1").sku("SUB-30")
				));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}

	private OrderRequest buildSponsorshipRequestBody(String id) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");
		
		ApplicationContext applicationContext = new ApplicationContext().brandName("YOURNEY").landingPage("BILLING")
				.cancelUrl(paypalCallbackUrl + "/paypal/cancel").returnUrl(paypalCallbackUrl + "/paypal/capture").userAction("CONTINUE");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId(id)
				.description("Patrocinio de un punto de interés por 30 días").softDescriptor("SPONSORSHIP")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("EUR").value(SPONSORSHIP_PRICE)
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("EUR").value(SPONSORSHIP_PRICE))))
				.items(List.of(
					new Item().name("Patrocinio").description("Patrocinio de un punto de interés por 30 días")
						.unitAmount(new Money().currencyCode("EUR").value(SPONSORSHIP_PRICE))
						.quantity("1").sku("SPO-30")
				));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}


	public HttpResponse<Order> createOrder(String orderType, String id) throws IOException, CheckoutException {
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
				throw new CheckoutException();
		}

		return paypalClient.execute(request);
	}

}
