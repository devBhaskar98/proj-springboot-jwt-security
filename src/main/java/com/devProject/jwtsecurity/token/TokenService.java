package com.devProject.jwtsecurity.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	@Autowired
	private final TokenRepository tokenRepository;
	
	public boolean deleteTokenById(Integer userId) {
		Token tokenDelete = tokenRepository.findById(userId).orElseThrow( () ->  new RuntimeException("Token is not found"));
		
		tokenRepository.deleteById(tokenDelete.getId());
		return true;
		
	}
	
}
