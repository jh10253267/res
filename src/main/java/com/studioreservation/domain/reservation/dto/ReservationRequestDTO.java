package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

import com.studioreservation.domain.reservation.enums.PayTyp;

import lombok.Getter;

@Getter
public class ReservationRequestDTO {
	private String date;
	private int hr;
	private String phone;
	private PayTyp payTyp;
	private String purpose;
	private int userCnt;
	private String state;
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
