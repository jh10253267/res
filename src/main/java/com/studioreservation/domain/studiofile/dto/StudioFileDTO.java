package com.studioreservation.domain.studiofile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudioFileDTO {
    private String fileName;
    private String savedFileName;
    private String savedFileUrl;
}
