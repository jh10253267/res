package com.studioreservation.domain.platform.entity;

import com.studioreservation.domain.platform.dto.PlatformReqDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Platform {
    @Id
    private Long cd;
    private String platform;
    private String memo;

    public void update(PlatformReqDTO reqDTO) {
        this.cd = reqDTO.getCd();
        this.platform = reqDTO.getPlatform();
        this.memo = reqDTO.getMemo();
    }
}
