package com.devProject.jwtsecurity.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@RequestMapping("/api/v1/management")
//@Tag(name = "Management")
public class ManagementController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authService;

	@Operation(description = "Get endpoint for manager", summary = "This is a summary for management get endpoint", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") }

	)
	@GetMapping
	@PreAuthorize("hasAuthority('management:read')")
	public List<User> get() {
		return userService.getManagerUsers();
	}

	@PostMapping
	public ResponseEntity<AuthenticationResponse> post(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PutMapping("/{userId}")
	public String put(@PathVariable int userId, @RequestBody UpdateUserRequest request) {
		return userService.updateUser(userId, request);
	}

	@DeleteMapping("/{userId}")
	public String delete(@PathVariable Integer userId) {
		// ERROR: update or delete on table "_user" violates foreign key constraint "fkiblu4cjwvyntq3ugo31klp1c6" on table "token"
//		return userService.deleteUserById(userId);
		return "DEL: MANAGER Controller";
	}
}
