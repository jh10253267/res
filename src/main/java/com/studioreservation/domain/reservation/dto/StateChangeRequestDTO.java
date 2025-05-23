package com.studioreservation.domain.reservation.dto;

import lombok.Data;

@Data
public class StateChangeRequestDTO {
	private String phone;
	private String resvCd;
	private String state;
}
