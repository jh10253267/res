package com.studioreservation.domain.purpose.entity;

import com.studioreservation.domain.purpose.dto.PurposeRequestDTO;
import com.studioreservation.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Purpose extends BaseEntity {
    @Id
    private Long cd;
    private String title;

    public void updatePurpose(PurposeRequestDTO requestDTO) {
        if(requestDTO.getTitle() != null) {
            this.title = requestDTO.getTitle();
        }
        if (requestDTO.getCd() != null) {
            this.cd = requestDTO.getCd();
        }
    }
}
