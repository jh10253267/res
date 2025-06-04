package com.studioreservation.domain.shootingrequest.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.shootingrequest.enums.ShootingTyp;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShootingRequestDTO {
	@Schema(example = "PHOTO or VIDEO", description = "촬영 유형")
	private ShootingTyp shootingTyp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "희망 착수일", description = "희망 착수일")
	private Timestamp strtDt;

	@Schema(example = "분량", description = "분량")
	private String quantity;

	@Schema(example = "상세 정보", description = "상세 정보")
	private String description;

	@Schema(example = "레퍼런스 링크", description = "레퍼런스 링크")
	private String refLink;

	@Schema(example = "개인정보 처리방침 동의 여부", description = "개인정보 처리방침 동의 여부")
	private boolean policyConfirmed;

	@Schema(example = "회사소개서 받아보기", description = "회사소개서 받아보기")
	private boolean newsConfirmed;

	@Schema(example = "카테고리 아이디", description = "카테고리 아이디")
	private Long purposeCd;
}