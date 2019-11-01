package io.jctiru.springbootsandbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jctiru.springbootsandbox.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findUserByEmail(String email);
	
}
