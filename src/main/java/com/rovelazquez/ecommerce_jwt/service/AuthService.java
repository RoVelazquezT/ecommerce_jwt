package com.rovelazquez.ecommerce_jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rovelazquez.ecommerce_jwt.dto.AuthResponse;
import com.rovelazquez.ecommerce_jwt.dto.LoginRequest;
import com.rovelazquez.ecommerce_jwt.dto.RegisterRequest;
import com.rovelazquez.ecommerce_jwt.entity.Role;
import com.rovelazquez.ecommerce_jwt.entity.userApp;
import com.rovelazquez.ecommerce_jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public AuthResponse login(LoginRequest request, HttpServletResponse response) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails user = (UserDetails) authentication.getPrincipal();

		String token = jwtService.getToken(user);

		Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);  
        jwtCookie.setPath("/");      
        jwtCookie.setMaxAge(24 * 60 * 60); 

        response.addCookie(jwtCookie); 
        return AuthResponse.builder().token(token).build();
	}

	public AuthResponse register(RegisterRequest request, HttpServletResponse response) {
		userApp user = userApp.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.role(Role.USER)
				.build();

		userRepository.save(user);
		
		String token = jwtService.getToken(user); 

        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); 
        
        response.addCookie(jwtCookie);
		return AuthResponse.builder().token(token).build(); 
	}


}
