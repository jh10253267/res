package com.studioreservation.domain.purpose.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurposeRequestDTO {
    @Schema(description = "정수형 기본 키 값(직접 입력)", example = "정수형 기본 키 값(직접 입력)")
    private Long cd;
    @Schema(description = "유튜브, 웨딩, etc", example = "유튜브, 웨딩, etc")
    private String title;
}
