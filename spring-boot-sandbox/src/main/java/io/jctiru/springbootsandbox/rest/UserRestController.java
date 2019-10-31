package io.jctiru.springbootsandbox.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserRestController {

	@GetMapping
	public String getUser() {
		return "Get user called";
	}

	@PostMapping
	public String createUser() {
		return "Create user called";
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
