package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studioreservation.domain.reservation.enums.PayTyp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDTO {
	private Long sn;
	private Long roomCd;
	private String userNm;
	private String phone;
	private PayTyp payTyp;
	private int userCnt;
	private String state;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	private Timestamp strtDt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	private Timestamp endDt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	private Timestamp regDt;
	private boolean useParking;
	private boolean needTaxInvoce;
	private String senderNm;
	private String proposal;
	private String requestCont;
	private boolean policyConfirmed;
	private String resvCd;

}
