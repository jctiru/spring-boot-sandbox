package io.jctiru.springbootsandbox.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jctiru.springbootsandbox.exception.UserServiceException;
import io.jctiru.springbootsandbox.service.UserService;
import io.jctiru.springbootsandbox.shared.dto.UserDto;
import io.jctiru.springbootsandbox.ui.model.request.UserDetailsRequestModel;
import io.jctiru.springbootsandbox.ui.model.response.ErrorMessages;
import io.jctiru.springbootsandbox.ui.model.response.OperationStatusModel;
import io.jctiru.springbootsandbox.ui.model.response.RequestOperationName;
import io.jctiru.springbootsandbox.ui.model.response.RequestOperationStatus;
import io.jctiru.springbootsandbox.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserRestController {

	@Autowired
	UserService userService;

	ModelMapper modelMapper = new ModelMapper();

	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = modelMapper.map(userDto, UserRest.class);
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserDto userDto = userService.getUserByUserId(id);
		return modelMapper.map(userDto, UserRest.class);
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		if (userDetails.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createdUser = userService.createUser(userDto);

		return modelMapper.map(createdUser, UserRest.class);
	}

	@PutMapping(path = "/{id}")
	public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto updatedUser = userService.updateUser(id, userDto);

		return modelMapper.map(updatedUser, UserRest.class);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESSS.name());

		return returnValue;
	}

}
