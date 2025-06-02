package com.studioreservation.domain.shootingrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.shootingrequest.entity.ShootingRequest;

public interface ShootingRequestRepository extends JpaRepository<ShootingRequest, Long>,
	JpaSpecificationExecutor<ShootingRequest> {

}
