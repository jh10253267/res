package com.studioreservation.global.request.enums;

import com.querydsl.core.types.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortDirection {
    ASC(Order.ASC),
    DESC(Order.DESC);

    private final Order querydslOrder;
}
