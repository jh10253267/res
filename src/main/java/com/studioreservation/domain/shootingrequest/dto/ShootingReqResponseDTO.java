package com.studioreservation.domain.shootingrequest.dto;

import java.sql.Timestamp;

import com.studioreservation.domain.shootingrequest.enums.ShootingTyp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShootingReqResponseDTO {
	@Schema(description = "영상/사진", example = "영상/사진")
	private ShootingTyp shootingTyp;

	@Schema(description = "목적 id", example = "목적 아이디")
	private Long purposeCd;

	@Schema(description = "대여 목적", example = "대여 목적")
	private String purpose;

	@Schema(description = "예약 시간", example = "예약 시간")
	private String strtDt;

	@Schema(description = "총량", example = "총량")
	private String quantity;

	private String description;

	private String refLink;

	private boolean policyConfirmed;

	private boolean newsConfirmed;

	private Timestamp createdAt;

	private Timestamp modifiedAt;
}
