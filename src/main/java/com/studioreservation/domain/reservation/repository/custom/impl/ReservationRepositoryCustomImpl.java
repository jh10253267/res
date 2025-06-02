package com.studioreservation.domain.reservation.repository.custom.impl;

import static com.studioreservation.domain.reservation.entity.QReservationHistory.*;

import java.sql.Timestamp;
import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
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
	public Page<ReservationResponseDTO> findPagedEntities(PageRequestDTO requestDTO, Long roomCd) {
		Pageable pageable = requestDTO.getPageable(requestDTO.getSortBy());

		List<ReservationResponseDTO> content = jpaQueryFactory
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
					reservationHistory.resvCd))
			.from(reservationHistory)
			.where(betweenStrtDtAndEndDt(requestDTO.getStrtDt(),
				requestDTO.getEndDt()), eqRoomCd(roomCd))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(createOrderSpecifier(pageable))
			.fetch();

		long totalCount = jpaQueryFactory
			.select(reservationHistory.count())
			.from(reservationHistory)
			.where(betweenStrtDtAndEndDt(requestDTO.getStrtDt(),
				requestDTO.getEndDt()), eqRoomCd(roomCd))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private BooleanExpression eqRoomCd(Long roomCd) {
		if (roomCd == null) {
			return null;
		}
		return reservationHistory.room.cd.eq(roomCd);
	}

	private BooleanExpression betweenStrtDtAndEndDt(Timestamp strtDt, Timestamp endDt) {
		if (strtDt == null || endDt == null) {
			return null;
		}

		if (endDt.before(strtDt)) {
			return null;
		}

		return reservationHistory.strtDt.between(strtDt, endDt);
	}

	private OrderSpecifier<?> createOrderSpecifier(Pageable pageable) {
		if(!pageable.getSort().isSorted()) {
			return null;
		}

		Sort.Order order = pageable.getSort().iterator().next();
		PathBuilder pathBuilder = new PathBuilder(reservationHistory.getType(), reservationHistory.getMetadata());

		return new OrderSpecifier(order.isAscending() ? Order.ASC: Order.DESC, pathBuilder.get(order.getProperty()));
	}

}
