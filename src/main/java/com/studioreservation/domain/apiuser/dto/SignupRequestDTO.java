package com.studioreservation.domain.apiuser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDTO {
	private String username;
	private String password;
}
