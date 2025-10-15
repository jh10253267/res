package com.studioreservation.domain.dailyrevenue.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyRevenueDTO {
    private Date salesDate;
    private BigDecimal dailyTotal;
}
