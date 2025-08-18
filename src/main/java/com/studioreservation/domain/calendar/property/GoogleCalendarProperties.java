package com.studioreservation.domain.calendar.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "google.calendar")
public class GoogleCalendarProperties {
    private String calendarId;
    private String applicationName;
    private String attendeeEmail;
    private String serviceAccountKeyBase64;
}
