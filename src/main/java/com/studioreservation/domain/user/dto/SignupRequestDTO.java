package com.studioreservation.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDTO {
	private String username;
	private String password;
}
