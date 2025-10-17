package com.studioreservation.domain.room.entity;

import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.enums.RoomType;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.*;
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

	private int halfHrPrice;

	private int dayPrice;

	private int minTm;

	private int capacity;

	private String title;

	private String description;

	private boolean useYn;

	@Enumerated(EnumType.STRING)
	private RoomType roomType;

    private Integer orderIndex;

	public void updateEntity(RoomRequestDTO requestDTO) {
		if (requestDTO.getName() != null) {
			this.name = requestDTO.getName();
		}
		if (requestDTO.getHalfHrPrice() != null) {
			this.halfHrPrice = requestDTO.getHalfHrPrice();
		}
		if (requestDTO.getDayPrice() != null) {
			this.dayPrice = requestDTO.getDayPrice();
		}
		if (requestDTO.getMinTm() != null) {
			this.minTm = requestDTO.getMinTm();
		}
		if (requestDTO.getCapacity() != null) {
			this.capacity = requestDTO.getCapacity();
		}
		if (requestDTO.getTitle() != null) {
			this.title = requestDTO.getTitle();
		}
		if (requestDTO.getDescription() != null) {
			this.description = requestDTO.getDescription();
		}
		if(requestDTO.getUseYn() != null) {
			this.useYn = requestDTO.getUseYn();
		}
        if(requestDTO.getOrderIndex() != null) {
            this.orderIndex = requestDTO.getOrderIndex();
        }
	}
}
