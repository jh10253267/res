package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDTO {
	@Schema(description = "예약자 명", example = "예약자 명")
	private String userNm;

	@Schema(description = "연락처", example = "연락처")
	private String phone;

	@Schema(description = "결재 수단", example = "결재 수단")
	private PayTyp payTyp;

	@Schema(description = "예약 인원수", example = "예약 인원수")
	private int userCnt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "예약 시작 시각", description = "예약 시작 시각")
	private Timestamp strtDt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "예약 종료 시각", description = "예약 종료 시각")
	private Timestamp endDt;

	@Schema(description = "주차권 필요 여부", example = "주차권 필요 여부")
	private boolean useParking;

	@Schema(description = "세금 계산서 필요 여부", example = "세금 계산서 필요 여부")
	private boolean needTaxInvoce;

	@Schema(description = "잘 모르겠음...", example = "잘 모르겠음...")
	private String senderNm;

	@Schema(description = "사용 목적", example = "사용 목적")
	private String proposal;

	@Schema(description = "잘 모르겠음...", example = "잘 모르겠음...")
	private String requestCont;

	@Schema(description = "약관 동의 여부", example = "약관 동의 여부")
	private boolean policyConfirmed;

}
