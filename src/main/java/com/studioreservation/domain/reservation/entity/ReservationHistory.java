package com.studioreservation.domain.reservation.entity;

import java.sql.Timestamp;

import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;
	private String userNm;
	private String phone;
	@Enumerated(EnumType.STRING)
	private PayTyp payTyp;
	private int userCnt;
	private String state;
	private Timestamp strtDt;
	private Timestamp endDt;
	private boolean useParking;
	private boolean needTaxInvoce;
	private String senderNm;
	private String proposal;
	private String requestCont;
	private boolean policyConfirmed;
	@Setter
	private String resvCd;
	@ManyToOne(fetch = FetchType.LAZY)
	@Setter
	private Room room;
}
