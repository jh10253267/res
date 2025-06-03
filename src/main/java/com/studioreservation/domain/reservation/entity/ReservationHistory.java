package com.studioreservation.domain.reservation.entity;

import java.sql.Timestamp;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.Column;
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

	@Builder.Default
	private String state = "00";

	private Timestamp strtDt;

	private Timestamp endDt;

	private Boolean useParking;

	private Boolean needTaxInvoce;

	private String senderNm;

	private String proposal;

	private String requestCont;

	private boolean policyConfirmed;
	private String memo;
	private String totalAmount;

	@Setter
	@Column(unique = true)
	private String resvCd;

	@ManyToOne(fetch = FetchType.LAZY)
	@Setter
	private Room room;

	public void changeState(String state) {
		this.state = state;
	}

	public void updateReservation(ReservationChangeRequestDTO dto) {
		if (dto.getUserNm() != null) this.userNm = dto.getUserNm();
		if (dto.getPhone() != null) this.phone = dto.getPhone();
		if (dto.getPayTyp() != null) this.payTyp = dto.getPayTyp();
		if (dto.getState() != null) this.state = dto.getState();
		if (dto.getUserCnt() != null) this.userCnt = dto.getUserCnt();
		if (dto.getStrtDt() != null) this.strtDt = dto.getStrtDt();
		if (dto.getEndDt() != null) this.endDt = dto.getEndDt();
		if (dto.getUseParking() != null) this.useParking = dto.getUseParking();
		if (dto.getNeedTaxInvoce() != null) this.needTaxInvoce = dto.getNeedTaxInvoce();
		if (dto.getSenderNm() != null) this.senderNm = dto.getSenderNm();
		if (dto.getProposal() != null) this.proposal = dto.getProposal();
		if (dto.getRequestCont() != null) this.requestCont = dto.getRequestCont();
		if (dto.getPolicyConfirmed() != null) this.policyConfirmed = dto.getPolicyConfirmed();
	}

}
