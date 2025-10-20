package com.studioreservation.domain.room.entity;

import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.enums.RoomType;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;

	private String name;

	private int capacity;

	private String title;

	private String description;

    private Integer orderIndex;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoomInfo> roomInfos = new ArrayList<>();

	public void updateEntity(RoomRequestDTO requestDTO) {
		if (requestDTO.getName() != null) {
			this.name = requestDTO.getName();
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
        if(requestDTO.getOrderIndex() != null) {
            this.orderIndex = requestDTO.getOrderIndex();
        }
	}
}
