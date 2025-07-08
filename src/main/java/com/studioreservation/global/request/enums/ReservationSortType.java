package com.studioreservation.global.request.enums;

import com.querydsl.core.types.Expression;
import com.studioreservation.domain.reservation.entity.QReservationHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationSortType {
    SN(QReservationHistory.reservationHistory.sn),
    CREATEDAT(QReservationHistory.reservationHistory.createdAt),
    STRTDT(QReservationHistory.reservationHistory.strtDt);

    private final Expression<? extends Comparable> path;

}
