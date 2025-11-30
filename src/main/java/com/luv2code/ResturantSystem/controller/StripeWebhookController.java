package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.service.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final OrderService orderService;

    public StripeWebhookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request,
                                                @RequestHeader("Stripe-Signature") String sigHeader) {

        StringBuilder payload = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                payload.append(line);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error reading payload");
        }

        Event event;
        try {
            event = Webhook.constructEvent(
                    payload.toString(), sigHeader, webhookSecret
            );
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        switch (event.getType()) {
            case "checkout.session.completed":
                String orderId = event.getData().getObject()
                        .getMetadata().get("orderId");

                orderService.updateOrderStatus(Integer.parseInt(orderId));
                break;
        }

        return ResponseEntity.ok("Received");
    }
}
