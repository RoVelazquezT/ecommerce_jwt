package com.rovelazquez.ecommerce_jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovelazquez.ecommerce_jwt.entity.userApp;

public interface UserRepository extends JpaRepository<userApp, Integer>{

	Optional<userApp> findByUsername(String Username);
	
}
