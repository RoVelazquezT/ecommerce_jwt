package com.rovelazquez.ecommerce_jwt.service;

import com.rovelazquez.ecommerce_jwt.entity.CartItem;
import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.entity.userApp;
import com.rovelazquez.ecommerce_jwt.repository.CartItemRepository;
import com.rovelazquez.ecommerce_jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository; 

    
    @Transactional
    public void addProductToCart(Long productId, userApp user, int quantity) {
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserAndProduct(user, product);

        if (cartItemOptional.isPresent()) {
            CartItem existingItem = cartItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .user(user)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(newItem);
        }
    }
    
    public List<CartItem> getCartItems(userApp user) {
        return cartItemRepository.findAllByUser(user);
    }
    
    public Double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
    
    @Transactional
    public void updateQuantity(Long cartItemId, int quantity, userApp user) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para modificar este item");
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }
    
    @Transactional
    public void removeProductFromCart(Long cartItemId, userApp user) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este item");
        }
        cartItemRepository.delete(item);
}
}