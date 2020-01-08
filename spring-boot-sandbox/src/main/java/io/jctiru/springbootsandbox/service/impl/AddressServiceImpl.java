package io.jctiru.springbootsandbox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jctiru.springbootsandbox.io.entity.AddressEntity;
import io.jctiru.springbootsandbox.io.entity.UserEntity;
import io.jctiru.springbootsandbox.io.repository.AddressRepository;
import io.jctiru.springbootsandbox.io.repository.UserRepository;
import io.jctiru.springbootsandbox.service.AddressService;
import io.jctiru.springbootsandbox.shared.dto.AddressDto;
import io.jctiru.springbootsandbox.ui.model.response.ErrorMessages;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<AddressDto> getAddresses(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + ": " + userId);
		}

		List<AddressEntity> addressesList = addressRepository.findAllByUser(userEntity);

		for (AddressEntity addressEntity : addressesList) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}

		return returnValue;
	}

}
