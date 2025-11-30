package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Order;
import com.luv2code.ResturantSystem.payment.StripePaymentService;
import com.luv2code.ResturantSystem.service.OrderService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    private final StripePaymentService stripePaymentService;
    private final OrderService orderService;

    @Autowired
    public StripeController(StripePaymentService stripePaymentService, OrderService orderService) {
        this.stripePaymentService = stripePaymentService;
        this.orderService = orderService;
    }

    @PostMapping("/create-checkout-session/{userId}")
    public ResponseEntity<?> createCheckoutSession(@PathVariable int userId) throws StripeException {
        Order order = orderService.checkout(userId); // create order from cart

        String sessionUrl = stripePaymentService.createCheckoutSession(order);

        return ResponseEntity.ok(Map.of(
                "checkout_url", sessionUrl,
                "order_id", order.getId()
        ));
    }

}
