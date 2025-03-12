package com.studioreservation.global.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.user.dto.APIUserDTO;
import com.studioreservation.domain.user.entity.APIUser;
import com.studioreservation.domain.user.repository.APIUserRepository;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioreservationApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
	private final APIUserRepository apiUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		APIUser apiUser = apiUserRepository.findById(username).orElseThrow();

		APIUserDTO apiUserDTO = new APIUserDTO(
			apiUser.getUsername(),
			apiUser.getPassword(),
			List.of(new SimpleGrantedAuthority(apiUser.getRole().getAuthority())));

		return apiUserDTO;
	}
}
