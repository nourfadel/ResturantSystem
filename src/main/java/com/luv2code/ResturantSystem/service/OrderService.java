package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Order;
import com.luv2code.ResturantSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order){
        order.setCreated_at(LocalDateTime.now());
        order.setUpdated_at(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }


    public List<Order> findUserOrders(int id){
        return orderRepository.findByUserId(id);
    }

    public Optional<Order> getOrderById(int id){
        return orderRepository.findById(id);
    }

}
