package com.studioreservation.global.request;

import java.sql.Timestamp;

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
	@Builder.Default
	private int page = 1;
	@Builder.Default
	private int size = Integer.MAX_VALUE;
	@Builder.Default
	private boolean desc = true;
	@Builder.Default
	private String sortBy = "createdAt";
	@Schema(type = "string", example = "20230519123045", description = "yyyyMMddHHmmss 형식의 날짜시간 문자열")
	private Timestamp strtDt;
	@Schema(type = "string", example = "20230519123045", description = "yyyyMMddHHmmss 형식의 날짜시간 문자열")
	private Timestamp endDt;


	public Pageable getPageable(String props) {
		if(size != Integer.MAX_VALUE) {
			Sort sort = desc ? Sort.by(props).descending() : Sort.by(props).ascending();
			return PageRequest.of(this.page - 1, this.size, sort);
		}

		return Pageable.unpaged();
	}
}
