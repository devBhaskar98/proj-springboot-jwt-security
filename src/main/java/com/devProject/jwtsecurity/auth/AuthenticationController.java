package com.devProject.jwtsecurity.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import common.model.ErrorResponse;
import common.model.RegisterRequest;
import common.model.ValidationErrorResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            // If there are validation errors, return a response with errors
            return ResponseEntity.badRequest().body(validationErrors(bindingResult));
        }
		
		try {
			return ResponseEntity.ok(service.register(request));
		} catch (TransactionSystemException ex) {
			return ResponseEntity.badRequest().body(ErrorResponse.builder().msg("Invalid Register Request").build());
		}
		
	}

	@PostMapping("/token")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {

		try {
			return ResponseEntity.ok(service.authenticate(request));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(ErrorResponse.builder().msg(e.getMessage()).build());
		}

	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.refreshToken(request, response);
	}
	
	
	// Helper method to extract validation errors from BindingResult
    private ValidationErrorResponse validationErrors(BindingResult bindingResult) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        bindingResult.getFieldErrors().forEach(error ->
                errorResponse.addError(error.getField(), error.getDefaultMessage()));
        return errorResponse;
    }
}
