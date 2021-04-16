package com.yourney.paypal.controller;

import java.util.Optional;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.yourney.model.Landmark;
import com.yourney.model.dto.Message;
import com.yourney.paypal.service.CaptureOrderService;
import com.yourney.paypal.service.CreateOrderService;
import com.yourney.security.model.User;
import com.yourney.security.service.UserService;
import com.yourney.service.LandmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private LandmarkService landmarkService;

    @Value("${paypal.frontend-url}")
    private String frontendUrl;

    private static final String ERROR_URI = "/error";

    @GetMapping("/create/{orderType}")
    public ResponseEntity<?> create(@PathVariable String orderType, @RequestParam(name = "id", defaultValue = "0", required = false) Long landmarkId) {
        String id = "";

        String currentUser = userService.getCurrentUsername();
        if (currentUser.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Un usuario anónimo no puede mejorar su plan"));
        }

        switch (orderType.toUpperCase()) {
            case "SUBSCRIPTION":
                Optional<User> findUser =  userService.getByUsername(currentUser);

                if (!findUser.isPresent()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario actual no existe"));
                }

                User user = findUser.get();
                id = user.getId().toString();
                break;

            case "SPONSORSHIP":
                
                if (landmarkId == 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("No se ha intoducido un id válido."));
                }

                Optional<Landmark> findLandmark = landmarkService.findById(landmarkId);

                if (!findLandmark.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe una ubicación con el id indicado"));
                }

                id = landmarkId.toString();
                break;
        
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("El tipo de pago es inválido"));
        }

        try {
            HttpResponse<Order> orderResponse = createOrderService.createOrder(orderType.toUpperCase(), id);
            if (orderResponse.statusCode() == 201) {
                return ResponseEntity.ok(new Message(orderResponse.result().links().get(1).href()));
            }

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Error al realizar la solicitud de pago"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Error al realizar la solicitud de pago"));
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
