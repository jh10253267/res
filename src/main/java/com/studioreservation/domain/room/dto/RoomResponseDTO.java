package com.studioreservation.domain.room.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoomResponseDTO {
	private Long cd;
	private String name;
	private String hrPrice;
	private String dayPrice;
	private int minTm;
	private int capacity;
	private String title;
	private String description;
}
