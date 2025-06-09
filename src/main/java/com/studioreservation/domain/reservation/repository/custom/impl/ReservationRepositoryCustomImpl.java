package com.studioreservation.domain.reservation.repository.custom.impl;

import static com.studioreservation.domain.reservation.entity.QReservationHistory.*;

import java.sql.Timestamp;
import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.studioreservation.domain.reservation.dto.ReservedTimeReqDTO;
import com.studioreservation.domain.reservation.dto.ReservedTimeResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studioreservation.domain.reservation.dto.ReservationResponseDTO;
import com.studioreservation.domain.reservation.repository.custom.ReservationRepositoryCustom;
import com.studioreservation.global.request.PageRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReservationResponseDTO> findPagedEntities(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(requestDTO.getSortBy());
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(pageable);

        JPAQuery<ReservationResponseDTO> query = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReservationResponseDTO.class,
                                reservationHistory.sn,
                                reservationHistory.room.cd,
                                reservationHistory.userNm,
                                reservationHistory.phone,
                                reservationHistory.payTyp,
                                reservationHistory.userCnt,
                                reservationHistory.state,
                                reservationHistory.strtDt,
                                reservationHistory.endDt,
                                reservationHistory.createdAt,
                                reservationHistory.useParking,
                                reservationHistory.needTaxInvoce,
                                reservationHistory.senderNm,
                                reservationHistory.proposal,
                                reservationHistory.requestCont,
                                reservationHistory.policyConfirmed,
                                reservationHistory.resvCd,
                                reservationHistory.totalAmount
                        )
                )
                .from(reservationHistory)
                .where(
                        betweenStrtDtAndEndDt(requestDTO.getStrtDt(), requestDTO.getEndDt()),
                        eqRoomCd(requestDTO.getRoomCd()),
                        eqPhone(requestDTO.getPhone()));

        if (pageable.isPaged()) {
            query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        if (orderSpecifier != null) {
            query.orderBy(orderSpecifier);
        }

        List<ReservationResponseDTO> content = query.fetch();


        Long totalCount = jpaQueryFactory
                .select(reservationHistory.count())
                .from(reservationHistory)
                .where(betweenStrtDtAndEndDt(requestDTO.getStrtDt(),
                                requestDTO.getEndDt()),
                        eqRoomCd(requestDTO.getRoomCd()),
                        eqPhone(requestDTO.getPhone()))
                .fetchOne();
        long safeCount = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, safeCount);
    }

    @Override
    public List<ReservedTimeResDTO> findReservedTime(Timestamp strtDt, Timestamp endDt) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReservedTimeResDTO.class,
                                reservationHistory.strtDt,
                                reservationHistory.endDt
                        )).from(reservationHistory)
                .where(betweenStrtDtAndEndDt(strtDt, endDt))
                .orderBy(reservationHistory.strtDt.asc())
                .fetch();
    }

    private BooleanExpression eqRoomCd(Long roomCd) {
        if (roomCd == null) {
            return null;
        }

        return reservationHistory.room.cd.eq(roomCd);
    }

    private BooleanExpression eqPhone(String phone) {
        if (phone == null) {
            return null;
        }

        return reservationHistory.phone.eq(phone);
    }

    private BooleanExpression betweenStrtDtAndEndDt(Timestamp strtDt, Timestamp endDt) {
        if (strtDt != null && endDt != null) {
            if (endDt.before(strtDt)) {
                return null;
            }
            return reservationHistory.strtDt.between(strtDt, endDt);
        } else if (strtDt != null) {
            return reservationHistory.strtDt.goe(strtDt);
        } else if (endDt != null) {
            return reservationHistory.strtDt.loe(endDt);
        } else {
            return null;
        }
    }


    private OrderSpecifier<?> createOrderSpecifier(Pageable pageable) {
        if (!pageable.getSort().isSorted()) {
            return null;
        }

        Sort.Order order = pageable.getSort().iterator().next();
        PathBuilder pathBuilder = new PathBuilder(reservationHistory.getType(), reservationHistory.getMetadata());

        return new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty()));
    }

}
