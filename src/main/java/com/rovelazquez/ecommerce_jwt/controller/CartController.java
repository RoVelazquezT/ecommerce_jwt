package com.rovelazquez.ecommerce_jwt.controller;

import com.rovelazquez.ecommerce_jwt.entity.userApp;
import com.rovelazquez.ecommerce_jwt.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart") 
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addProductToCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal userApp user,
            @RequestParam(name = "quantity", defaultValue = "1") int quantity 
    ) {

        if (user == null) {
            return new ResponseEntity<>("{\"error\": \"User not authenticated\"}", HttpStatus.UNAUTHORIZED);
        }

        try {
            cartService.addProductToCart(productId, user, quantity);
            
            return ResponseEntity.ok("{\"message\": \"Product added successfully\"}");
        } catch (RuntimeException e) {
            return new ResponseEntity<>("{\"error\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<String> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam("quantity") int quantity,
            @AuthenticationPrincipal userApp user) {

        if (user == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            cartService.updateQuantity(cartItemId, quantity, user);
            return ResponseEntity.ok("Quantity updated");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long cartItemId,
            @AuthenticationPrincipal userApp user) {

        if (user == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            cartService.removeProductFromCart(cartItemId, user);
            return ResponseEntity.ok("Item removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}