package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

import com.studioreservation.domain.reservation.enums.PayTyp;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationRequestDTO {
	private String userNm;
	private String phone;
	private PayTyp payTyp;
	private int userCnt;
	@Builder.Default
	private String state = "00";
	private Timestamp strtDt;
	private Timestamp endDt;
	private Timestamp regDt;
	private boolean useParking;
	private boolean needTaxInvoce;
	private String senderNm;
	private String proposal;
	private String requestCont;
	private boolean policyConfirmed;
}
