package com.studioreservation.domain.reservation.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.util.Calculator;
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

	@Setter
	private Integer totalAmount;

	@Setter
	@Column(unique = true)
	private String resvCd;

	@ManyToOne(fetch = FetchType.LAZY)
	@Setter
	private Room room;

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
		if (dto.getMemo() != null) this.memo = dto.getMemo();
	}

	public void calculateTotalAmount(Room room, Calculator calculator) {
		this.room = room;
		int duration = calculator.calculate(strtDt, endDt);
		double discountRate = 0.0;

		if(isEvening(this.strtDt)) {
			discountRate += 0.2;
		}
		if(duration >= 8) {
			discountRate += 0.1;
		}

		this.totalAmount = applyDiscount(room.getHrPrice(), discountRate);
	}

	private boolean isEvening(Timestamp resvDuration) {
		LocalDateTime time = resvDuration.toLocalDateTime();
		return time.getHour() >= 18;
	}

	private int applyDiscount(int price, double discountRate) {
		return (int) (price * (1 - discountRate));
	}
}
