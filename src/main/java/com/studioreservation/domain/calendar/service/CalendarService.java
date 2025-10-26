package com.studioreservation.domain.calendar.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.studioreservation.domain.calendar.dto.CalendarMetaDTO;
import com.studioreservation.domain.calendar.dto.CalendarRequestDTO;
import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.calendar.property.GoogleCalendarProperties;
import com.studioreservation.domain.calendar.repository.CalendarRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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

        String base64Key = props.getServiceAccountKeyBase64();
        InputStream keyStream;

        if (base64Key != null && !base64Key.isEmpty()) {
            byte[] decodedKey = Base64.getDecoder().decode(base64Key.trim());
            keyStream = new ByteArrayInputStream(decodedKey);
        } else {
            keyStream = new FileInputStream("src/main/resources/service-account.json");
        }

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(keyStream)
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/calendar"));

        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(props.getApplicationName()).build();
    }

    public PageResponseDTO<CalendarMetaDTO> getAllMetaData(PageRequestDTO requestDTO) {
        Page<CalendarMetaDTO> result = repository.findPagedEntities(requestDTO);

        return PageResponseDTO.<CalendarMetaDTO>withAll()
                .dtoList(result.getContent())
                .pageRequestDTO(requestDTO)
                .total(result.getTotalElements())
                .build();
    }

    public String addEvent(CalendarRequestDTO dto) throws Exception {
        Event event = new Event()
                .setSummary(dto.getTitle())
                .setDescription("자동으로 등록된 일정입니다.");

        event.setStart(new EventDateTime()
                .setDateTime(dto.getStartDateTime())
                .setTimeZone("Asia/Seoul"));

        event.setEnd(new EventDateTime()
                .setDateTime(dto.getEndDateTime())
                .setTimeZone("Asia/Seoul"));

        Event createdEvent = calendarClient.events()
                .insert(props.getCalendarId(), event).execute();

        CalendarMetaData metaData = CalendarMetaData.builder()
                .calendarId(props.getCalendarId())
                .applicationName(props.getApplicationName())
                .attendeeEmail(props.getAttendeeEmail())
                .htmlLink(createdEvent.getHtmlLink())
                .scheduleId(createdEvent.getId())
                .eventId(createdEvent.getId())
                .build();

        repository.save(metaData);

        return createdEvent.getHtmlLink();
    }

    public void updateCalendar(CalendarRequestDTO calendarRequestDTO) throws IOException {
        Long sn = calendarRequestDTO.getDto().getSn();
        CalendarMetaData calendarMetaData = repository.findByReservationSn(sn);
        String eventId = calendarMetaData.getEventId();

        Event event = calendarClient.events()
                .get(props.getCalendarId(), eventId)
                .execute();
        event.setStart(new EventDateTime()
                .setDateTime(calendarRequestDTO.getStartDateTime())
                .setTimeZone("Asia/Seoul"));
        event.setEnd(new EventDateTime()
                .setDateTime(calendarRequestDTO.getEndDateTime())
                .setTimeZone("Asia/Seoul"));

        calendarClient.events()
                .update(props.getCalendarId(), eventId, event)
                .execute();
    }

    public void deleteCalendar(Long sn) throws IOException {
        CalendarMetaData calendarMetaData =repository.findByReservationSn(sn);
        String eventId = calendarMetaData.getEventId();

        calendarClient.events().delete(props.getCalendarId(), eventId).execute();
    }

}