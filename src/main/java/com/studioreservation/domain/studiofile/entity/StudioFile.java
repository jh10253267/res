package com.studioreservation.domain.studiofile.entity;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import org.hibernate.validator.constraints.Length;

import com.studioreservation.domain.shootingrequest.entity.ShootingRequest;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioFile extends BaseEntity {
	@Id
	private String uuid;

	private String fileName;

	@Length(max = 500)
	private String savedFileUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShootingRequest shootingRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationHistory reservationHistory;

	public void changeRequest(ShootingRequest shootingRequest) {
		this.shootingRequest = shootingRequest;
	}

}