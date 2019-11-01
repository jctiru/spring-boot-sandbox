package io.jctiru.springbootsandbox.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jctiru.springbootsandbox.dto.UserDto;
import io.jctiru.springbootsandbox.entity.UserEntity;
import io.jctiru.springbootsandbox.repository.UserRepository;
import io.jctiru.springbootsandbox.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto user) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		userEntity.setEncryptedPassword("test");
		userEntity.setUserId("test");

		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

}
