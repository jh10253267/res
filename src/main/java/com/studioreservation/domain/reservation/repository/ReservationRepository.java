package com.studioreservation.domain.reservation.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.studioreservation.domain.reservation.entity.ReservationHistory;

public interface ReservationRepository extends JpaRepository<ReservationHistory, Long>,
	JpaSpecificationExecutor<ReservationHistory> {

	// Slice<ReservationHistory> findTopByCreatedAt();
	// Slice<ReservationHistory> findTopByCreatedAtAndRoom_Cd(Long roomCd);

}
