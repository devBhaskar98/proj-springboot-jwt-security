package com.devProject.jwtsecurity.demo;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

	@Operation(summary = "Get list of admins")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of Admins", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
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

	@DeleteMapping("{userId}")
	@PreAuthorize("hasAuthority('admin:delete')")
	@Hidden
	public String delete(@PathVariable int userId) {
		return userService.deleteUserById(userId);
	}
}
