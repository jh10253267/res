package com.studioreservation.domain.reservation.dto.response;

import com.studioreservation.domain.dailyrevenue.dto.DailyRevenueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class RevenueResponseDTO {
    BigDecimal totalRevenue;
    List<DailyRevenueDTO> dailyRevenue;
}
