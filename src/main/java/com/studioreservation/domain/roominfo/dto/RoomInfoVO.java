package com.studioreservation.domain.roominfo.dto;

import com.studioreservation.domain.room.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomInfoVO {
    private Long cd;
    private BigDecimal extraPayPerPerson;
    private BigDecimal halfHrPrice;
    private BigDecimal dayPrice;
    private String description;
    private int minTm;
    private RoomType roomType;
}
