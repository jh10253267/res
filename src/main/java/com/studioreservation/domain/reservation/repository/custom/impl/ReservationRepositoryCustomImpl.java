package com.studioreservation.domain.reservation.repository.custom.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studioreservation.domain.reservation.dto.*;
import com.studioreservation.domain.reservation.enums.ReservationState;
import com.studioreservation.domain.reservation.repository.custom.ReservationRepositoryCustom;
import com.studioreservation.global.request.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

import static com.studioreservation.domain.reservation.entity.QReservationHistory.reservationHistory;

@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReservationAdminResponseDTO> findPagedEntities(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                requestDTO.getSortDir().getQuerydslOrder(),
                requestDTO.getSortBy().getPath());

        JPAQuery<ReservationAdminResponseDTO> query = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReservationAdminResponseDTO.class,
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
                                reservationHistory.totalRevenue,
                                reservationHistory.commission,
                                reservationHistory.income,
                                reservationHistory.memo
                        )
                )
                .from(reservationHistory)
                .where(
                        betweenStrtDtAndEndDt(requestDTO.getStrtDt(), requestDTO.getEndDt()),
                        eqRoomCd(requestDTO.getRoomCd()),
                        eqPhone(requestDTO.getPhone()))
                .orderBy(orderSpecifier);

        if (pageable.isPaged()) {
            query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        List<ReservationAdminResponseDTO> content = query.fetch();


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
    public List<ReservedTimeResDTO> findReservedTime(Timestamp strtDt, Timestamp endDt, Long roomCd) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReservedTimeResDTO.class,
                                reservationHistory.strtDt,
                                reservationHistory.endDt
                        )).from(reservationHistory)
                .where(betweenStrtDtAndEndDt(strtDt, endDt),
                        reservationHistory.state.eq(ReservationState.CONFIRMED),
                        eqRoomCd(roomCd))
                .orderBy(reservationHistory.strtDt.asc())
                .fetch();
    }

    @Override
    public Integer sumTotalAmount(Timestamp strtDt, Timestamp endDt) {
        return jpaQueryFactory
                .select(reservationHistory.totalRevenue.sum())
                .from(reservationHistory)
                .where(betweenStrtDtAndEndDt(strtDt, endDt))
                .fetchOne();
    }

    @Override
    public ReservationStateResponse findCountByState(Timestamp strtDt,
                                                Timestamp endDt) {
        List<StateCountDTO> result = jpaQueryFactory
                .select(Projections.constructor(StateCountDTO.class,
                        reservationHistory.state,
                        reservationHistory.count()
                ))
                .from(reservationHistory)
                .groupBy(reservationHistory.state)
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(reservationHistory.count())
                .from(reservationHistory)
                .fetchOne();

        long safeCount = totalCount != null ? totalCount : 0L;

        return new ReservationStateResponse(safeCount, result);
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
}
