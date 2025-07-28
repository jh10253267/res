package com.studioreservation.domain.calendar.repository.custom;

import com.studioreservation.domain.calendar.dto.CalendarMetaDTO;
import com.studioreservation.global.request.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface CalendarRepositoryCustom {
    Page<CalendarMetaDTO> findPagedEntities(PageRequestDTO requestDTO);
}
