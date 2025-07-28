package com.studioreservation.domain.calendar.mapper;

import com.studioreservation.domain.calendar.dto.CalendarMetaDTO;
import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    CalendarMetaDTO toDTO(CalendarMetaData metaData);
}