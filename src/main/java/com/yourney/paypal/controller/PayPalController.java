package com.yourney.paypal.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PurchaseUnit;
import com.paypal.payments.Refund;
import com.yourney.paypal.service.CaptureOrderService;
import com.yourney.paypal.service.CreateOrderService;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paypal")
@CrossOrigin
public class PayPalController {

    @Autowired
    private CaptureOrderService captureOrderService;

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private UserService userService;

    @Value("${paypal.frontend-url}")
    private String frontendUrl;

    @GetMapping("/create/{orderType}")
    public String create(@PathVariable String orderType) {
        String redirectUrl = "redirect:" + frontendUrl;
        String id = "";

        switch (orderType.toUpperCase()) {
            case "SUBSCRIPTION":
                
                String currentUser = userService.getCurrentUsername();
                if (currentUser.equals("anonymousUser")) {
                    return redirectUrl + "/error";
                }

                Optional<User> findUser =  userService.getByUsername(currentUser);

                if (!findUser.isPresent()) {
                    return redirectUrl + "/error";
                }

                User user = findUser.get();
                id = user.getId().toString();

                break;

            case "SPONSORSHIP":
                
                break;
        
            default:
                break;
        }

        try {
            // Creating Order
            HttpResponse<Order> orderResponse = createOrderService.createOrder(orderType.toUpperCase(), id);
            System.out.println("Creating Order...");
            if (orderResponse.statusCode() != 201){
                return redirectUrl + "/error";         
            } else if (orderResponse.statusCode() == 201) {
                return "redirect:" + orderResponse.result().links().get(1).href();
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return redirectUrl + "/error";
    }

    @GetMapping("/capture")
    public String capture(@RequestParam("token") String token, @RequestParam("PayerID") String payerId) {

        String redirectUrl = "redirect:" + frontendUrl;

        try {
            // Capturing Order
            System.out.println("Capturing Order...");
            String returnUrl = captureOrderService.captureOrder(token);
            redirectUrl =  redirectUrl + returnUrl;

        } catch(Exception e){
            return redirectUrl + "/error";
        }

        return redirectUrl;
    }
}
