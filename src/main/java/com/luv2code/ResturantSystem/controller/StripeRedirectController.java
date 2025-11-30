package com.luv2code.ResturantSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeRedirectController {

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess() {
        return ResponseEntity.ok("Payment Successful! 🎉");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancelled() {
        return ResponseEntity.ok("Payment Cancelled ❌");
    }
}
