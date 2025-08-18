package com.studioreservation.domain.calendar.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studioreservation.domain.calendar.dto.CalendarMetaDTO;
import com.studioreservation.domain.calendar.repository.custom.CalendarRepositoryCustom;
import com.studioreservation.global.request.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.studioreservation.domain.calendar.entity.QCalendarMetaData.calendarMetaData;

@RequiredArgsConstructor
public class CalendarRepositoryCustomImpl implements CalendarRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CalendarMetaDTO> findPagedEntities(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable();

        JPAQuery<CalendarMetaDTO> query = jpaQueryFactory
                .select(
                        Projections.constructor(
                                CalendarMetaDTO.class,
                                calendarMetaData.cd,
                                calendarMetaData.calendarId,
                                calendarMetaData.scheduleId,
                                calendarMetaData.applicationName,
                                calendarMetaData.attendeeEmail
                        )
                )
                .from(calendarMetaData);

        if (pageable.isPaged()) {
            query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        List<CalendarMetaDTO> content = query.fetch();

        Long totalCount = jpaQueryFactory
                .select(calendarMetaData.count())
                .from(calendarMetaData)
                .fetchOne();
        long safeCount = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, safeCount);
    }
}
