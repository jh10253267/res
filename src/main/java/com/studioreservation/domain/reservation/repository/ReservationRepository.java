package com.studioreservation.domain.reservation.repository;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.repository.custom.ReservationRepositoryCustom;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    @Query(value = "SELECT DATE(strt_dt) AS sales_date, SUM(total_revenue) AS daily_total " +
            "FROM reservation_history " +
            "WHERE strt_dt >= :startDt AND strt_dt < :endDt " +
            "  AND state in ('confirmed', 'completed')" +
            "GROUP BY DATE(strt_dt) " +
            "ORDER BY DATE(strt_dt)",
            nativeQuery = true)
    List<Object[]> findDailyRevenueNative(
            @Param("startDt") Timestamp startDt,
            @Param("endDt") Timestamp endDt);


    @Query(value = "SELECT SUM(total_revenue)" +
            "FROM reservation_history " +
            "WHERE strt_dt >= :startDt AND strt_dt < :endDt " +
            "  AND state in ('confirmed', 'completed') ",
            nativeQuery = true)
    BigDecimal getTotalRevenue(@Param("startDt") Timestamp startDt,
                               @Param("endDt") Timestamp endDt);
}
