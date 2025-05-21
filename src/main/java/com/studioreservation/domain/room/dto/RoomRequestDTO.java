package com.studioreservation.domain.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomRequestDTO {
	@Schema(description = "방 이름", example = "방 이름")
	private String name;

	@Schema(description = "시간당 가격", example = "시간당 가격")
	private Integer hrPrice;

	@Schema(description = "일별 가격", example = "일별 가격")
	private Integer dayPrice;

	@Schema(description = "최소 대여 시간", example = "최소 대여 시간")
	private Integer minTm;

	@Schema(description = "수용 가능 인원", example = "수용 가능 인원")
	private Integer capacity;

	@Schema(description = "방 호칭", example = "방 호칭")
	private String title;

	@Schema(description = "방 설명", example = "방 설명")
	private String description;

	@Schema(description = "사용 가능 여부", example = "사용 가능 여부")
	private Boolean useYn;
}

