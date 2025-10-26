package com.studioreservation.domain.calendar.repository;

import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.calendar.repository.custom.CalendarRepositoryCustom;
import com.studioreservation.domain.reservation.entity.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarMetaData, Long>, CalendarRepositoryCustom {
    default CalendarMetaData findByReservationSn(Long sn) {
        return findByReservationHistory_Sn(sn).orElseThrow();
    }
    Optional<CalendarMetaData> findByReservationHistory_Sn(Long reservationHistorySn);
}
