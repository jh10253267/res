package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRequestDTO {
	private String userNm;
	private String phone;
	private PayTyp payTyp;
	private int userCnt;
	@Builder.Default
	private String state = "00";
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "20230519123045", description = "yyyyMMddHHmmss 형식의 날짜시간 문자열")
	private Timestamp strtDt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "20230519123045", description = "yyyyMMddHHmmss 형식의 날짜시간 문자열")
	private Timestamp endDt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@Schema(type = "string", example = "20230519123045", description = "yyyyMMddHHmmss 형식의 날짜시간 문자열")
	@JsonDeserialize(using = TimestampDeserializer.class)
	private Timestamp regDt;
	private boolean useParking;
	private boolean needTaxInvoce;
	private String senderNm;
	private String proposal;
	private String requestCont;
	private boolean policyConfirmed;
}
