package com.rovelazquez.ecommerce_jwt.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;    
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })

public class userApp implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(nullable = false )
	String username;
	@Column(nullable = false) 
    @JsonIgnore
	String password;
	@Column(nullable = false )
	String lastname;
	@Column(nullable = false )
	String firstname;
	@Column(nullable = false )
	@Email(message = "Debe ingresar un correo electrónico válido") 
    @NotBlank(message = "El email no puede estar vacío") 
	String email; 
	
	String address;
	@Column(nullable = true )
	String city; 
	@Column(nullable = true )
	String postcode; 
	@Column(nullable = true, length = 20)
	String movile; 
	
	@Enumerated(EnumType.STRING)
	Role role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	} 
	
	@Override
	public boolean isAccountNonExpired() {
		return true; 
	}
	@Override
	public boolean isAccountNonLocked() {
		return true; 
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true; 
	}
	@Override
	public boolean isEnabled() {
		return true; 
	}
	
}
