package com.studioreservation.domain.apiuser.entity;

import com.studioreservation.domain.apiuser.enums.UserRoleEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "API_USER")
public class APIUser {
	@Id
	private String username;
	private String password;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private UserRoleEnum role = UserRoleEnum.USER;

	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
}
