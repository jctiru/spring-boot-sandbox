package io.jctiru.springbootsandbox.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jctiru.springbootsandbox.exception.UserServiceException;
import io.jctiru.springbootsandbox.service.UserService;
import io.jctiru.springbootsandbox.shared.dto.UserDto;
import io.jctiru.springbootsandbox.ui.model.request.UserDetailsRequestModel;
import io.jctiru.springbootsandbox.ui.model.response.ErrorMessages;
import io.jctiru.springbootsandbox.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserRestController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		if (userDetails.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return "Update user called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "Delete user called";
	}

}
