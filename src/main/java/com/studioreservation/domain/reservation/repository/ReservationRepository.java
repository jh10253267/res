package com.studioreservation.domain.reservation.repository;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.repository.custom.ReservationRepositoryCustom;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationHistory, Long>, ReservationRepositoryCustom {

	Optional<ReservationHistory> findByPhoneAndResvCd(String phone, String resvCd);

	default ReservationHistory findReservationHistory(String phone, String resvCd) {
		return findByPhoneAndResvCd(phone, resvCd).orElseThrow(() ->
				new StudioException(ErrorCode.NO_SUCH_RESERVATION));
	}

	boolean existsByResvCd(String resvCd);

    List<ReservationHistory> findByEndDtBeforeAndState(LocalDateTime endDt, ReservationState state);

    @Query("SELECT SUM(r.totalAmount) FROM ReservationHistory r " +
            "WHERE r.state = :state AND r.strtDt >= :start AND r.strtDt < :end")
    Integer getTotalRevenueForDate(
            @Param("state") ReservationState state,
            @Param("start") Timestamp start,
            @Param("end") Timestamp end
    );
}
