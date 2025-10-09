package com.studioreservation.domain.shootingrequest.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studioreservation.domain.shootingrequest.enums.ShootingTyp;
import com.studioreservation.domain.studiofile.dto.StudioFileDTO;
import com.studioreservation.global.formatter.TimestampDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShootingRequestDTO {
	@Schema(example = "PHOTO or VIDEO", description = "촬영 유형")
	private ShootingTyp shootingTyp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
	@JsonDeserialize(using = TimestampDeserializer.class)
	@Schema(type = "string", example = "희망 착수일", description = "희망 착수일")
	private Timestamp strtDt;

    @NotBlank(message = "이름을 입력하세요.")
    @Pattern(
            regexp = "^[가-힣]{2,10}$",
            message = "이름은 2~10자의 한글만 가능합니다."
    )
    @Schema(example = "이름", description = "이름")
    private String username;

    @NotBlank(message = "연락처를 입력하세요.")
    @Pattern(
            regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$",
            message = "유효한 휴대폰 번호 형식이 아닙니다."
    )
    @Schema(example = "연락처", description = "연락처")
    private String phone;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Schema(example = "이메일", description = "이메일")
    private String email;

	@Schema(example = "분량", description = "분량")
	private String quantity;

	@Schema(example = "상세 정보", description = "상세 정보")
	private String description;

	@Schema(example = "레퍼런스 링크", description = "레퍼런스 링크")
	private String refLink;

	@Schema(example = "개인정보 처리방침 동의 여부", description = "개인정보 처리방침 동의 여부")
	private boolean policyConfirmed;

	@Schema(example = "회사소개서 받아보기", description = "회사소개서 받아보기")
	private boolean newsConfirmed;

	@Schema(example = "카테고리 아이디", description = "카테고리 아이디")
	private Long purposeCd;

	@Schema(example = "파일 이름")
	private List<StudioFileDTO> fileNames;
}