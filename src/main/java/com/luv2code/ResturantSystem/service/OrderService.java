package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Order;
import com.luv2code.ResturantSystem.entity.Resturant;
import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.repository.OrderRepository;
import com.luv2code.ResturantSystem.repository.ResturantRepository;
import com.luv2code.ResturantSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ResturantRepository resturantRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ResturantRepository resturantRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.resturantRepository = resturantRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(Order order){

        int userId = order.getUser().getId();
        int restaurantId = order.getResturant().getId();

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Resturant existingRestaurant = resturantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        order.setUser(existingUser);
        order.setResturant(existingRestaurant);
        order.setStatus("Pending");
        order.setCreated_at(LocalDateTime.now());
        order.setUpdated_at(LocalDateTime.now());

        return orderRepository.save(order);
    }


    public List<Order> findUserOrders(int id){
        return orderRepository.findByUserId(id);
    }

    public Optional<Order> getOrderById(int id){
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
