package com.studioreservation.domain.roominfo.entity;

import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.room.enums.RoomType;
import com.studioreservation.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd;
    private BigDecimal halfHrPrice;
    private BigDecimal dayPrice;
    private String description;
    private int minTm;
    @ManyToOne
    private Room room;
    private boolean useYn;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private BigDecimal extraPayPerPerson;
    private int orderIndex;

    public void updateRoomInfo(RoomRequestDTO requestDTO) {

    }
}
