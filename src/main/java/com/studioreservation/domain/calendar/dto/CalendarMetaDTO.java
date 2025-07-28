package com.studioreservation.domain.calendar.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarMetaDTO {
    private Long cd;
    private String calendarId;
    private String scheduleId;
    private String htmlLink;
    private String applicationName;
    private String attendeeEmail;
}
