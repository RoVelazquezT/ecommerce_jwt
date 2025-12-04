package com.rovelazquez.ecommerce_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rovelazquez.ecommerce_jwt.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration 
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationProvider authProvider; 
	private final JwtAuthenticationFilter jwtAuthenticacionFilter; 
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
	
	return http 
			.csrf(csrf ->
				csrf
					.disable())
			.authorizeHttpRequests(authRequest ->
				authRequest
				
                .requestMatchers("/", "/home", "/register", "/login",
                                 "/testimonial", "/contact", "/shop/**", "/auth/**")
                .permitAll()
                .requestMatchers("/css/**", "/js/**", "/lib/**", "/img/**")
                .permitAll()

                .requestMatchers("/carrito").authenticated()
                .requestMatchers("/checkout").authenticated()
                .requestMatchers("/cart/**").authenticated()
                .requestMatchers("/order-success").authenticated()

                .anyRequest().authenticated()
        )
			
			 .sessionManagement(session ->
             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					 )
			 .authenticationProvider(authProvider)
			 .addFilterBefore(jwtAuthenticacionFilter, UsernamePasswordAuthenticationFilter.class)

			.build();
}
}
