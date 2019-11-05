package io.jctiru.springbootsandbox.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import io.jctiru.springbootsandbox.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	UserDto getUserByUserId(String userId);

	UserDto updateUser(String userId, UserDto user);

}
