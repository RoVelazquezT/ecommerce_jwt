package com.rovelazquez.ecommerce_jwt.repository;

import com.rovelazquez.ecommerce_jwt.entity.CartItem;
import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.entity.userApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    
    Optional<CartItem> findByUserAndProduct(userApp user, Product product);
    
    List<CartItem> findAllByUser(userApp user); 
} 