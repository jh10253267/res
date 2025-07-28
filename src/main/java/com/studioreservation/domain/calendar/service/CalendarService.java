package com.studioreservation.domain.calendar.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.studioreservation.domain.calendar.dto.CalendarMetaDTO;
import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.calendar.property.GoogleCalendarProperties;
import com.studioreservation.domain.calendar.repository.CalendarRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.Collections;

@Service
public class CalendarService {
    private final CalendarRepository repository;
    private final GoogleCalendarProperties props;
    private final Calendar calendarClient;

    public CalendarService(GoogleCalendarProperties props,
                           CalendarRepository repository) throws Exception {
        this.props = props;
        this.repository = repository;

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(props.getServiceAccountKeyFile()))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/calendar"));

        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(props.getApplicationName()).build();
    }

    public String addEvent(String title, DateTime startDateTime, DateTime endDateTime) throws Exception {
        Event event = new Event()
                .setSummary(title)
                .setDescription("자동으로 등록된 일정입니다.");

        event.setStart(new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul"));

        event.setEnd(new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul"));

        Event createdEvent = calendarClient.events()
                .insert(props.getCalendarId(), event).execute();

        CalendarMetaData metaData = CalendarMetaData.builder()
                .calendarId(props.getCalendarId())
                .applicationName(props.getApplicationName())
                .attendeeEmail(props.getAttendeeEmail())
                .htmlLink(createdEvent.getHtmlLink())
                .scheduleId(createdEvent.getId())
                .build();

        repository.save(metaData);

        return createdEvent.getHtmlLink(); // 생성된 일정 링크 리턴
    }

    public PageResponseDTO<CalendarMetaDTO> getAllMetaData(PageRequestDTO requestDTO) {
        Page<CalendarMetaDTO> result = repository.findPagedEntities(requestDTO);

        return PageResponseDTO.<CalendarMetaDTO>withAll()
                .dtoList(result.getContent())
                .pageRequestDTO(requestDTO)
                .total(result.getTotalElements())
                .build();
    }
}