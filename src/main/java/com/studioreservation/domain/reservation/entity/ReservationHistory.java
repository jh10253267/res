package com.studioreservation.domain.reservation.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.studioreservation.domain.reservation.dto.ReservationChangeRequestDTO;
import com.studioreservation.domain.reservation.enums.PayTyp;
import com.studioreservation.domain.reservation.enums.Platform;
import com.studioreservation.domain.reservation.enums.ReservationState;
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
	@Enumerated(EnumType.STRING)
	ReservationState state = ReservationState.WAITING;

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

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Platform platform = Platform.HOMEPAGE;

	private static final double DEFAULT_DISCOUNT_RATE = 0.2;
	private static final int EXTRA_PAY_PER_PERSON = 5000;
	private static final int EVENING_HOUR = 18;

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

	private int calculateDurationHours() {
		long durationMillis = endDt.getTime() - strtDt.getTime(); // 밀리초 차이
		long durationHours = (long) Math.ceil(durationMillis / (1000.0 * 60 * 60)); // 올림 처리

		return (int) (durationHours);
	}


	public void calculateTotalAmount(Room room) {
		int duration = calculateDurationHours();
		int extraPay = applyExtraPay(room, this.userCnt);
		double discountRate = 0.0;

		if(isEvening(this.strtDt)) {
			discountRate += DEFAULT_DISCOUNT_RATE;
		} else if(duration >= 8) {
			discountRate += DEFAULT_DISCOUNT_RATE;
		}

		this.totalAmount = calculateTotal(room.getHrPrice(),
				duration,
				extraPay,
				discountRate);
	}

	private boolean isEvening(Timestamp resvDuration) {
		LocalDateTime time = resvDuration.toLocalDateTime();
		return time.getHour() >= EVENING_HOUR;
	}

	private double calculateDiscount(double discountRate) {
		return (1 - discountRate);
	}

	private int calculateTotal(int hrPrice, int duration, int extraPay, double discountRate) {
		return (int) (((hrPrice * duration) + extraPay) * calculateDiscount(discountRate));
	}

	private int applyExtraPay(Room room, int userCnt) {
		int extraPay = 0;
		// 만약 방의 수용 가능 인원보다 예약 희망 인원이 많다면
		if(room.getCapacity() < userCnt) {
			extraPay = (userCnt - room.getCapacity()) * EXTRA_PAY_PER_PERSON;
		}

		return extraPay;
	}
}
