package com.studioreservation.domain.roominfo.dto;

import com.studioreservation.domain.room.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoResponseDTO {
    @Schema(description = "방 식별 번호", example = "방 식별 번호")
    private Long cd;

    @Schema(description = "30분당 가격", example = "30분당 가격")
    private int halfHrPrice;

    @Schema(description = "일별 가격", example = "일별 가격")
    private int dayPrice;

    @Schema(description = "최소 대여 시간", example = "최소 대여 시간")
    private int minTm;

    @Schema(description = "수용 가능 인원", example = "수용 가능 인원")
    private int capacity;

    @Schema(description = "방 설명", example = "방 설명")
    private String description;

    @Schema(description = "방 설명", example = "방 설명")
    private String title;

    @Schema(description = "방 타입", example = "방 타입")
    private RoomType roomType;

    private Integer orderIndex;
}
