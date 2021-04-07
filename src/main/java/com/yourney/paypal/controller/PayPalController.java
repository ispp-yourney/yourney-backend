package com.yourney.paypal.controller;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PurchaseUnit;
import com.paypal.payments.Refund;
import com.yourney.paypal.service.CaptureOrderService;
import com.yourney.paypal.service.CreateOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paypal")
public class PayPalController {

    @Autowired
    private CaptureOrderService captureOrderService;

    @Autowired
    private CreateOrderService createOrderService;

    @GetMapping("/create")
    public String create() {
        String redirectUrl = "/";
        try {
            // Creating Order
            HttpResponse<Order> orderResponse = createOrderService.createOrder(false);
            String orderId = "";
            System.out.println("Creating Order...");
            if (orderResponse.statusCode() == 201){
                orderId = orderResponse.result().id();
                System.out.println("Links:");
                for (LinkDescription link : orderResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href());

                    if (link.rel().equals("approve")) {
                        redirectUrl = link.href();
                    }
                }
            }            

        } catch(Exception e){
            e.printStackTrace();
        }

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/capture")
    public String capture(@RequestParam("token") String token, @RequestParam("PayerID") String payerId) {
        try {

            // Capturing Order
            System.out.println("Capturing Order...");
            HttpResponse<Order> orderResponse = captureOrderService.captureOrder(token, false);
            String captureId = "";
            if (orderResponse.statusCode() == 201){
                System.out.println("Captured Successfully");
                System.out.println("Status Code: " + orderResponse.statusCode());
                System.out.println("Status: " + orderResponse.result().status());
                System.out.println("Order ID: " + orderResponse.result().id());
                System.out.println("Links:");
                for (LinkDescription link : orderResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href());
                }
                System.out.println("Capture ids:");
    			for (PurchaseUnit purchaseUnit : orderResponse.result().purchaseUnits()) {
    				for (Capture capture : purchaseUnit.payments().captures()) {
    					System.out.println("\t" + capture.id());
    					captureId = capture.id();
    				}
    			}
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return "redirect:http://localhost:8080/userProfile";
    }
}
