package com.studioreservation.domain.room.dto;

import com.studioreservation.domain.roominfo.dto.RoomInfoAdminResponseDTO;
import com.studioreservation.domain.roominfo.dto.RoomInfoResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAdminResponseDTO {
    @Schema(description = "방 식별 번호", example = "방 식별 번호")
    private Long cd;

    @Schema(description = "방 이름", example = "방 이름")
    private String name;

    @Schema(description = "수용 가능 인원", example = "수용 가능 인원")
    private int capacity;

    @Schema(description = "방 호칭", example = "방 호칭")
    private String title;

    @Schema(description = "방 설명", example = "방 설명")
    private String description;

    @Schema(description = "방 순서", example = "방 순서")
    private Integer orderIndex;

    @Schema(description = "방 순서", example = "방 순서")
    private List<RoomInfoAdminResponseDTO> roomInfos = new ArrayList<>();
}
