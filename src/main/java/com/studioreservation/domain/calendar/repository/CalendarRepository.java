package com.studioreservation.domain.calendar.repository;

import com.studioreservation.domain.calendar.entity.CalendarMetaData;
import com.studioreservation.domain.calendar.repository.custom.CalendarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarMetaData, Long>, CalendarRepositoryCustom {
}
