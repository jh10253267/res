package com.studioreservation.domain.apiuser.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRoleEnum {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private String authority;
}
