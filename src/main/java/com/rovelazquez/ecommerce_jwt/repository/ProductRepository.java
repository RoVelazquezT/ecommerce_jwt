package com.rovelazquez.ecommerce_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rovelazquez.ecommerce_jwt.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByNameContainingIgnoreCase(String query);
	List<Product> findFirst4ByCategoryAndIdNot(String category, Long id);
	@Query("SELECT p.category as category, COUNT(p) as count " +
	           "FROM Product p " +
	           "GROUP BY p.category")
	List<CategoryCount> countTotalProductsByCategory();
	List<Product> findByCategory(String category);
}
