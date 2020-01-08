package io.jctiru.springbootsandbox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jctiru.springbootsandbox.exception.UserServiceException;
import io.jctiru.springbootsandbox.io.entity.UserEntity;
import io.jctiru.springbootsandbox.io.repository.UserRepository;
import io.jctiru.springbootsandbox.service.UserService;
import io.jctiru.springbootsandbox.shared.Utils;
import io.jctiru.springbootsandbox.shared.dto.AddressDto;
import io.jctiru.springbootsandbox.shared.dto.UserDto;
import io.jctiru.springbootsandbox.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public UserDto createUser(UserDto user) {
		if (userRepository.findUserByEmail(user.getEmail()) != null) {
			throw new RuntimeException("Record already exists");
		}

		for (int i = 0; i < user.getAddresses().size(); i++) {
			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(50));
			user.getAddresses().set(i, address);
		}

		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		String publicUserId = utils.generateUserId(50);
		userEntity.setUserId(publicUserId);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity storedUserDetails = userRepository.save(userEntity);

		return modelMapper.map(storedUserDetails, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findUserByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findUserByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}

		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + ": " + userId);
		}

		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUserDetails = userRepository.save(userEntity);

		return modelMapper.map(updatedUserDetails, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}

		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		if (page > 0) {
			page = page - 1;
		}

		List<UserDto> returnValue = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageable);
		List<UserEntity> users = usersPage.getContent();

		for (UserEntity userEntity : users) {
			UserDto userDto = modelMapper.map(userEntity, UserDto.class);
			returnValue.add(userDto);
		}

		return returnValue;
	}

}
