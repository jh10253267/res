package com.studioreservation.domain.user.entity;

import org.springframework.security.core.userdetails.User;

import com.studioreservation.domain.user.enums.UserRoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
