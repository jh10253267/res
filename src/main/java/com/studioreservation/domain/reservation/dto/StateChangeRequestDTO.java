package com.studioreservation.domain.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StateChangeRequestDTO {
	@Schema(example = "핸드폰 번호")
	private String phone;
	@Schema(example = "예약 번호")
	private String resvCd;
	@Schema(example = "변경할 예약 상태")
	private String state;
}
