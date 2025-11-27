package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.Cart;
import com.luv2code.ResturantSystem.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // get cart for current user
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable int userId){
        return cartService.getUserCart(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addItemsToCart(@PathVariable int userId,
                               @RequestParam int menuId,
                               @RequestParam int quantity
                               ){
        return cartService.addItemToCart(userId,menuId,quantity);
    }

    @PutMapping("/{userId}/update")
    public Cart updateQuantity(@PathVariable int userId,
                               @RequestParam int cartItemId,
                               @RequestParam int quantity){
        return cartService.updateItemQuantity(userId,cartItemId,quantity);
    }

    @DeleteMapping("/{userId}/remove")
    public Cart remove(@PathVariable int userId,
                       @RequestParam int cartItemId){
        return cartService.removeItem(userId,cartItemId);
    }

    @DeleteMapping("/{userId}/remove")
    public String clearCart(@PathVariable int userId) {
        cartService.clearCart(userId);
        return "Cart Cleared Successfully";
    }

}
