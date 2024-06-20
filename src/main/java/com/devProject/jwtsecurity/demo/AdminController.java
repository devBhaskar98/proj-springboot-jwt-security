package com.devProject.jwtsecurity.demo;

import io.swagger.v3.oas.annotations.Hidden;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devProject.jwtsecurity.auth.AuthenticationResponse;
import com.devProject.jwtsecurity.auth.AuthenticationService;
import com.devProject.jwtsecurity.user.User;
import com.devProject.jwtsecurity.user.UserService;

import common.model.RegisterRequest;
import common.model.UpdateUserRequest;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authService;

	@GetMapping
	@PreAuthorize("hasAuthority('admin:read')")
	public List<User> get() {
		return userService.getAdminUsers();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('admin:create')")
	@Hidden
	public ResponseEntity<AuthenticationResponse> post(@RequestBody RegisterRequest request) {
		// add logic for data validation
		return ResponseEntity.ok(authService.register(request));
	}
	

	@PutMapping
	@PreAuthorize("hasAuthority('admin:update')")
	@Hidden
	public String put(@PathVariable int userId, @RequestBody UpdateUserRequest request) {
		return userService.updateUser(userId, request);
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin:delete')")
	@Hidden
	public String delete() {
		return "DELETE:: admin controller";
	}
}
