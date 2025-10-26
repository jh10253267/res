package com.studioreservation.domain.calendar.entity;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarMetaData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd;
    private String calendarId;
    private String scheduleId;
    private String htmlLink;
    private String applicationName;
    private String attendeeEmail;
    private String eventId;
    @OneToOne
    private ReservationHistory reservationHistory;
}
