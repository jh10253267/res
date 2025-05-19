package com.studioreservation.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomRequestDTO {
	private String name;
	private Integer hrPrice;
	private Integer dayPrice;
	private Integer minTm;
	private Integer capacity;
	private String title;
	private String description;
	private Boolean useYn;

}

