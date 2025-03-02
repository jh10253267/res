package com.studioreservation.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.reservation.entity.ReservationHistory;

public interface ReservationRepository extends JpaRepository<ReservationHistory, Long> {

}
