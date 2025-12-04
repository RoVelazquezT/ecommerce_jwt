package com.rovelazquez.ecommerce_jwt.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rovelazquez.ecommerce_jwt.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.http.Cookie; 

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{	
	
	private final JwtService jwtService; 
	private final UserDetailsService userDetailsService; 
	

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

      
        try {
            username = jwtService.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            
        } catch (ExpiredJwtException e) {
            System.out.println("El token ha expirado: " + e.getMessage());
            
            deleteCookie(response, "jwt_token");
            SecurityContextHolder.clearContext();
            
        } catch (Exception e) {
            System.out.println("Error en el token: " + e.getMessage());
            deleteCookie(response, "jwt_token");
            SecurityContextHolder.clearContext();
        }
       

        filterChain.doFilter(request, response);
    }

	
	
	
	private String getTokenFromRequest(HttpServletRequest request) {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(StringUtils.hasText(authHeader)&& authHeader.startsWith("Bearer "))
		{
			return authHeader.substring(7);
		}
		
		// 2. Si no está en la cabecera, buscar en las Cookies (para las páginas <a>)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
	
	}
	
	//Metodo para borrar la cookie 
    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 0 significa "borrar inmediatamente"
        response.addCookie(cookie);
    }

	
}




