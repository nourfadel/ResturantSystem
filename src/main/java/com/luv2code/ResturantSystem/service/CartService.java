package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.Cart;
import com.luv2code.ResturantSystem.entity.CartItem;
import com.luv2code.ResturantSystem.entity.Menu;
import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.repository.CartItemRepository;
import com.luv2code.ResturantSystem.repository.CartRepository;
import com.luv2code.ResturantSystem.repository.MenuRepository;
import com.luv2code.ResturantSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private MenuRepository menuRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository,
                       MenuRepository menuRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart getUserCart(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found!"));

        return cartRepository.findByUser(user)
                .orElseGet(()-> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItems(new ArrayList<>());
                    cart.setTotalPrice(0);
                    return cartRepository.save(cart);
                        });

    }

    public Cart addItemToCart(int userId, int menuId, int quantity){
        Cart cart = getUserCart(userId);

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("menu not found!"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setMenuItem(menu);
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * menu.getPrice());

        cart.getItems().add(item);
        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(int userId, int cartItemId, int quantity) {

        Cart cart = getUserCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId() == cartItemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart Item Not Found"));

        item.setQuantity(quantity);
        item.setTotalPrice(item.getMenuItem().getPrice() * quantity);

        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    public Cart removeItem(int userId, int cartItemId){
        Cart cart = getUserCart(userId);

        CartItem item = cart.getItems()
                    .stream()
                .filter(i -> i.getId() == cartItemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found!"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    public void clearCart(int userId){
        Cart cart = getUserCart(userId);
        cart.getItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {

        double total = 0;
        for (CartItem item : cart.getItems()){
            total += item.getTotalPrice();
        }
        cart.setTotalPrice(total);
    }


}
