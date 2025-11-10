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

    @OrderBy("orderIndex ASC")
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoomInfo> roomInfos = new ArrayList<>();

    public boolean isDiscountApplicable() {
        return "A".equalsIgnoreCase(name);
    }

	public void updateEntity(RoomRequestDTO requestDTO) {
        this.name = requestDTO.getName();
        this.title = requestDTO.getTitle();
        this.description = requestDTO.getDescription();
        this.capacity = requestDTO.getCapacity();
        this.orderIndex = requestDTO.getOrderIndex();
	}
}
