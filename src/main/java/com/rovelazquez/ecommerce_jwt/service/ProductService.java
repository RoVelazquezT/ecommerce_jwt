package com.rovelazquez.ecommerce_jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.repository.CategoryCount;
import com.rovelazquez.ecommerce_jwt.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
	
    public List<Product> searchProductsByName(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
    
    public List<Product> findRelatedProducts(String category, Long currentProductId) {
        return productRepository.findFirst4ByCategoryAndIdNot(category, currentProductId);
    }
    public List<CategoryCount> getCategoryCounts() {
        return productRepository.countTotalProductsByCategory();
    }
    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
