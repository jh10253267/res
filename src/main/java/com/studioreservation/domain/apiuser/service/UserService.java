package com.studioreservation.domain.apiuser.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.apiuser.dto.SignupRequestDTO;
import com.studioreservation.domain.apiuser.entity.APIUser;
import com.studioreservation.domain.apiuser.repository.APIUserRepository;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final APIUserRepository apiUserRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequestDTO signupRequestDTO) {
		if(apiUserRepository.existsById(signupRequestDTO.getUsername())) {
			throw new StudioException(ErrorCode.ALREADY_EXIST_USERNAME);
		}

		APIUser apiUser = APIUser.builder()
			.username(signupRequestDTO.getUsername())
			.password(passwordEncoder.encode(signupRequestDTO.getPassword()))
			.build();

		apiUserRepository.save(apiUser);
	}
}
