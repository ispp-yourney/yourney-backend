package com.yourney.paypal.controller;

import java.util.Optional;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.yourney.paypal.service.CaptureOrderService;
import com.yourney.paypal.service.CreateOrderService;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String ERROR_URI = "/error";

    @GetMapping("/create/{orderType}")
    public String create(@PathVariable String orderType) {
        String redirectUrl = "redirect:" + frontendUrl;
        String id = "";

        String currentUser = userService.getCurrentUsername();
        if (currentUser.equals("anonymousUser")) {
            return redirectUrl + ERROR_URI;
        }

        switch (orderType.toUpperCase()) {
            case "SUBSCRIPTION":
                Optional<User> findUser =  userService.getByUsername(currentUser);

                if (!findUser.isPresent()) {
                    return redirectUrl + ERROR_URI;
                }

                User user = findUser.get();
                id = user.getId().toString();
                break;

            case "SPONSORSHIP":
                id = "";
                break;
        
            default:
                return redirectUrl + ERROR_URI;
        }

        try {
            HttpResponse<Order> orderResponse = createOrderService.createOrder(orderType.toUpperCase(), id);
            if (orderResponse.statusCode() == 201) {
                return "redirect:" + orderResponse.result().links().get(1).href();
            } else {
                return redirectUrl + ERROR_URI;         
            }

        } catch(Exception e){
            return redirectUrl + ERROR_URI;
        }
    }

    @GetMapping("/capture")
    public String capture(@RequestParam("token") String token, @RequestParam("PayerID") String payerId) {

        String redirectUrl = "redirect:" + frontendUrl;

        try {
            String returnUrl = captureOrderService.captureOrder(token);
            redirectUrl =  redirectUrl + returnUrl;

        } catch(Exception e){
            return redirectUrl + ERROR_URI;
        }

        return redirectUrl;
    }
}
