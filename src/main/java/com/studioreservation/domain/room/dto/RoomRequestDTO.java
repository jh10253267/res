package com.studioreservation.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomRequestDTO {
	private String name;
	private String hrPrice;
	private String dayPrice;
	private int minTm;
	private int capacity;
	private String title;
	private String description;
}

