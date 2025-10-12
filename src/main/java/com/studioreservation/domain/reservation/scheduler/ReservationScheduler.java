package com.studioreservation.domain.reservation.scheduler;


import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationRepository reservationRepository;
    // 5분마다 수행
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateExpiredReservations() {
        log.info("----------------------스케쥴링 시작----------------------");

        LocalDateTime now = LocalDateTime.now();

        List<ReservationHistory> expiredList =
                reservationRepository.findByEndDtBeforeAndState(now, ReservationState.CONFIRMED);

        for (ReservationHistory reservation : expiredList) {
            reservation.setState(ReservationState.COMPLETED);
        }

        reservationRepository.saveAll(expiredList);

        log.info("----------------------스케쥴링 완료----------------------");
        log.info("총 {}건 업데이트됨", expiredList.size());
    }
}
