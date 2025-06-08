package com.studioreservation.domain.reservation.repository;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.repository.custom.ReservationRepositoryCustom;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationHistory, Long>, ReservationRepositoryCustom {

	Optional<ReservationHistory> findByPhoneAndResvCd(String phone, String resvCd);

	default ReservationHistory findReservationHistory(String phone, String resvCd) {
		return findByPhoneAndResvCd(phone, resvCd).orElseThrow(() ->
				new StudioException(ErrorCode.NO_SUCH_RESERVATION));
	}

	boolean existsByResvCd(String resvCd);
}
