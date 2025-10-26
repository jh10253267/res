package com.studioreservation.domain.calendar.handler;

import com.studioreservation.domain.calendar.service.CalendarService;
import com.studioreservation.domain.reservation.event.ReservationConfirmedEvent;
import com.studioreservation.domain.reservation.event.ReservationCanceledEvent;
import com.studioreservation.domain.reservation.event.ReservationUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalendarEventHandler {
    private final CalendarService calendarService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationConfirmed(ReservationConfirmedEvent event) {
        try {
            calendarService.addEvent(event.getCalendarRequestDTO());
            log.info("✅ 구글 캘린더 동기화 완료: {}", event.getCalendarRequestDTO().getDto().getResvCd());
        } catch (Exception e) {
            log.error("❌ 구글 캘린더 동기화 실패: {}", e.getMessage(), e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationUpdated(ReservationUpdatedEvent event) {
        try {
            calendarService.updateCalendar(event.getCalendarRequestDTO());
        } catch (Exception e) {
            log.error("❌ 구글 캘린더 동기화 실패: {}", e.getMessage(), e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationReservationDeletedEvent(ReservationCanceledEvent event) {
        try {
            calendarService.deleteCalendar(event.getSn());
        } catch (Exception e) {
            log.error("❌ 구글 캘린더 동기화 실패: {}", e.getMessage(), e);

        }
    }
}