package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Order;
import com.luv2code.ResturantSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/createOrder")
    public Order creatOrder(@RequestBody Order newOrder){
        return orderService.createOrder(newOrder);
    }

    @PostMapping("/{id}")
    public Order getOrderById(@PathVariable int id){
        return orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    // get user orders
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable int userId){
        return orderService.findUserOrders(userId);
    }

}
