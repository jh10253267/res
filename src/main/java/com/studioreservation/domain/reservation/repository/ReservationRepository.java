package com.studioreservation.domain.reservation.repository;

import java.util.Optional;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.studioreservation.domain.reservation.entity.ReservationHistory;

public interface ReservationRepository extends JpaRepository<ReservationHistory, Long>,
	JpaSpecificationExecutor<ReservationHistory> {

	Optional<ReservationHistory> findByPhoneAndResvCd(String phone, String resvCd);
	default ReservationHistory findReservationHistory(String phone, String resvCd) {
		return findByPhoneAndResvCd(phone, resvCd).orElseThrow(); // TODO 예외 처리 작성하기
	}
	boolean existsByResvCd(String resvCd);
}
