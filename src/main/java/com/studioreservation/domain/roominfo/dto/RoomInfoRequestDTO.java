package com.studioreservation.domain.roominfo.dto;

import com.studioreservation.domain.room.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomInfoRequestDTO {
    private String description;
    private BigDecimal halfHrPrice;
    private BigDecimal dayPrice;
    private BigDecimal extraPayPerPerson;
    private int minTm;
    private RoomType roomType;
    private Long roomCd;
    private boolean useYn;
    private Integer orderIndex;
}
