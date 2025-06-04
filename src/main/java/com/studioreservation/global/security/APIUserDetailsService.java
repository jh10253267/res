package com.studioreservation.global.security;

import java.util.List;

import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.apiuser.dto.APIUserDTO;
import com.studioreservation.domain.apiuser.entity.APIUser;
import com.studioreservation.domain.apiuser.repository.APIUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
	private final APIUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		APIUser apiUser = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		return new APIUserDTO(
				apiUser.getUsername(),
				apiUser.getPassword(),
				List.of(new SimpleGrantedAuthority(apiUser.getRole().getAuthority())));
	}
}
