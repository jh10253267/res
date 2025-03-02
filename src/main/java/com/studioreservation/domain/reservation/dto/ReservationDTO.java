package com.studioreservation.domain.reservation.dto;

import java.sql.Timestamp;

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
public class ReservationDTO {
	private Long sn;
	private Long roomCd;
	private String userNm;
	private String phone;
	private PayTyp payTyp;
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
