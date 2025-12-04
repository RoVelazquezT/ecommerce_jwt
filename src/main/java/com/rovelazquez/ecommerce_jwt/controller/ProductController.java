package com.rovelazquez.ecommerce_jwt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.repository.CategoryCount;
import com.rovelazquez.ecommerce_jwt.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@GetMapping("/shop")
	public String shop(Model model, 
	                   @RequestParam(name = "query", required = false) String query, 
	                   @RequestParam(name = "category", required = false) String category) {
	    
	    List<Product> products;

	    if (query != null && !query.isEmpty()) {
            products = productService.searchProductsByName(query);
            model.addAttribute("searchQuery", query);
            
        } else if (category != null && !category.isEmpty()) {
            products = productService.findProductsByCategory(category);
            model.addAttribute("selectedCategory", category); 
            
        } else {
            products = productService.findAllProducts();
        }
        
        List<CategoryCount> categories = productService.getCategoryCounts();
        model.addAttribute("categories", categories);

        model.addAttribute("products", products);
        return "product/shop";
    }
	
	@GetMapping("/shop/detail/{id}")
    public String shopDetail(@PathVariable Long id, Model model, @RequestParam(name = "query", required = false) String query) {
        
        Optional<Product> productOptional = productService.findProductById(id);
        
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            
            model.addAttribute("product", product);
            
    
            List<Product> relatedProducts = productService.findRelatedProducts(
                product.getCategory(), 
                product.getId()
            );
            model.addAttribute("relatedProducts", relatedProducts);
            
            List<CategoryCount> categories = productService.getCategoryCounts();
            model.addAttribute("categories", categories);
            
            return "product/shop-detail"; 
        } else {
            return "redirect:/shop";
        }
        
        
    }
	
}
