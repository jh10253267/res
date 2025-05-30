package com.studioreservation.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.user.dto.SignupRequestDTO;
import com.studioreservation.domain.user.entity.APIUser;
import com.studioreservation.domain.user.repository.APIUserRepository;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioreservationApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final APIUserRepository apiUserRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequestDTO signupRequestDTO) {
		if(apiUserRepository.existsById(signupRequestDTO.getUsername())) {
			throw new StudioreservationApplicationException(ErrorCode.USER_NOT_FOUND);
		}

		APIUser apiUser = APIUser.builder()
			.username(signupRequestDTO.getUsername())
			.password(passwordEncoder.encode(signupRequestDTO.getPassword()))
			.build();

		apiUserRepository.save(apiUser);
	}
}
