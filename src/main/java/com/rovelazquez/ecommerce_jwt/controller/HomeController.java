package com.rovelazquez.ecommerce_jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rovelazquez.ecommerce_jwt.entity.Product;
import com.rovelazquez.ecommerce_jwt.service.ProductService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class HomeController {

	@Autowired
	private final ProductService productService; 
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
	    List<Product> products = productService.findAllProducts();
	    model.addAttribute("products", products); 
		return "home/home";
	}
	

	@GetMapping("/register")
	public String from() {
		return "home/registerForm";
	}
	
	@GetMapping("/login")
	public String login() {
		return "home/loginForm"; 
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "home/contact";
	}
	
	@GetMapping("/testimonial")
	public String testimonial() {
		return "home/testimonial"; 
	}
}
