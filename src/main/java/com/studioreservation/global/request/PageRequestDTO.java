package com.studioreservation.global.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studioreservation.global.request.enums.ReservationSortType;
import com.studioreservation.global.request.enums.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
	@Schema(description = "요청 페이지", example = "요청 페이지")
	@Builder.Default
	private int page = 1;

	@Schema(description = "한번에 요청할 데이터 개수", example = "한번에 요청할 데이터 개수")
	@Builder.Default
	private int size = Integer.MAX_VALUE;

	@Schema(type = "string", example = "예약 시작 시각", description = "예약 시작 시각")
	private Timestamp strtDt;

	@Schema(type = "string", example = "예약 종료 시각", description = "예약 종료 시각")
	private Timestamp endDt;

	@Schema(type = "long", example = "방별 조회", description = "방별 조회")
	private Long roomCd;

	@Schema(type = "string", example = "핸드폰 번호로 조회", description = "핸드폰 번호로 조회")
	private String phone;

	@Schema(type = "string", example = "sortBy", description = "sortBy")
	@Builder.Default
	private ReservationSortType sortBy = ReservationSortType.CREATEDAT;

	@Schema(type = "string", example = "sortDir", description = "sortDir")
	@Builder.Default
	private SortDirection sortDir = SortDirection.DESC;

    @JsonIgnore
	public Pageable getPageable() {
		if(size != Integer.MAX_VALUE) {
			return PageRequest.of(this.page - 1, this.size);
		}

		return Pageable.unpaged();
	}
}
