package com.devProject.jwtsecurity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import common.model.UpdateUserRequest;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

		// check if the current password is correct
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new IllegalStateException("Wrong password");
		}
		// check if the two new passwords are the same
		if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
			throw new IllegalStateException("Password are not the same");
		}

		// update the password
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));

		// save the new password
		userRepository.save(user);
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user;
	}

	// MANAGERS

	public List<User> getManagerUsers() {
		User user = getCurrentUser();
//		System.out.println("Current User::" + user);
		List<User> users = userRepository.findByRole(user.getRole());

		return users;
	}

	public String updateUser(int userId, UpdateUserRequest user) {
		User updateUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		updateUser.setFirstname(user.getFirstname());
		updateUser.setLastname(user.getLastname());
		updateUser.setEmail(user.getEmail());
		updateUser.setPassword(user.getPassword()); // Ideally, you should encrypt the password before saving
//		updateUser.setRole(user.getRole());
		userRepository.save(updateUser);
		return "";
	}
	
	public String deleteUserById(int userId) {
		User updateUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.deleteById(updateUser.getId());
		return "Delete Successfully";
	}
}
