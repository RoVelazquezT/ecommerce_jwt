package com.rovelazquez.ecommerce_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rovelazquez.ecommerce_jwt.dto.AuthResponse;
import com.rovelazquez.ecommerce_jwt.dto.LoginRequest;
import com.rovelazquez.ecommerce_jwt.dto.RegisterRequest;
import com.rovelazquez.ecommerce_jwt.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private final AuthService authService; 

	
	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody 
			LoginRequest request, HttpServletResponse response) {
		return ResponseEntity.ok(authService.login(request, response));
	}
	
	@PostMapping("register")
	public ResponseEntity<AuthResponse> register (@RequestBody 
	        RegisterRequest request, HttpServletResponse response) {
	    	AuthResponse authResponse = authService.register(request, response); 
	    
	    return ResponseEntity.ok(authResponse); 
	}
	
	
}
