package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.*;
import com.luv2code.ResturantSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ResturantRepository resturantRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ResturantRepository resturantRepository, UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.resturantRepository = resturantRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuRepository = menuRepository;
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

    public Order checkout( int userId){


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found!"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is Empty!"));


        Order order = new Order();
        order.setUser(user);
        order.setStatus("Pending");
        order.setCreated_at(LocalDateTime.now());
        order.setUpdated_at(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem item : cart.getItems() ){
            Menu menu = item.getMenuItem();

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menu);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(menu.getPrice()   );
            orderItem.setTotalPrice( item.getQuantity() * menu.getPrice());
            orderItem.setOrder(order);

            total += orderItem.getTotalPrice();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        cartItemRepository.deleteAll(cart.getItems());

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
