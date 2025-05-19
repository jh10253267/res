package com.studioreservation.domain.room.entity;

import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;
	private String name;
	private int hrPrice;
	private int dayPrice;
	private int minTm;
	private int capacity;
	private String title;
	private String description;
	private boolean useYn;

	public void updateEntity(RoomRequestDTO roomRequestDTO) {
		if (roomRequestDTO.getName() != null) {
			this.name = roomRequestDTO.getName();
		}
		if (roomRequestDTO.getHrPrice() != null) {
			this.hrPrice = roomRequestDTO.getHrPrice();
		}
		if (roomRequestDTO.getDayPrice() != null) {
			this.dayPrice = roomRequestDTO.getDayPrice();
		}
		if (roomRequestDTO.getMinTm() != null) {
			this.minTm = roomRequestDTO.getMinTm();
		}
		if (roomRequestDTO.getCapacity() != null) {
			this.capacity = roomRequestDTO.getCapacity();
		}
		if (roomRequestDTO.getTitle() != null) {
			this.title = roomRequestDTO.getTitle();
		}
		if (roomRequestDTO.getDescription() != null) {
			this.description = roomRequestDTO.getDescription();
		}
		if(roomRequestDTO.getUseYn() != null) {
			this.useYn = roomRequestDTO.getUseYn();
		}
	}
}
