package io.jctiru.springbootsandbox.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.jctiru.springbootsandbox.io.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findUserByEmail(String email);

	UserEntity findByUserId(String userId);

}
