package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Order;
import com.luv2code.ResturantSystem.payment.StripePaymentService;
import com.luv2code.ResturantSystem.service.OrderService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final StripePaymentService stripePaymentService;

    @Autowired
    public OrderController(OrderService orderService, StripePaymentService stripePaymentService) {
        this.orderService = orderService;
        this.stripePaymentService = stripePaymentService;
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/createOrder")
    public Order creatOrder(@RequestBody Order newOrder){
        return orderService.createOrder(newOrder);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id){
        return orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    // get user orders
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable int userId){
        return orderService.findUserOrders(userId);
    }

    // add checkout method and payment url
    @PostMapping("/checkout/{userId}")
    public Map<String,Object> checkout(@PathVariable int userId) throws StripeException {

        Order order = orderService.checkout(userId);

        String paymentUrl = stripePaymentService.createCheckoutSession(order);

        // response
        Map<String,Object> response = new HashMap<>();
        response.put("orderId",order.getId());
        response.put("amount",order.getTotalAmount());
        response.put("paymentUrl",paymentUrl);

        return response;
    }

}
