package com.studioreservation.domain.featuretoggle.mapper;

import com.studioreservation.domain.featuretoggle.dto.FeatureToggleReqDTO;
import com.studioreservation.domain.featuretoggle.dto.FeatureToggleResDTO;
import com.studioreservation.domain.featuretoggle.entity.FeatureToggle;
import com.studioreservation.domain.reservation.mapper.ReservationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeatureToggleMapper {
    FeatureToggleMapper INSTANCE = Mappers.getMapper(FeatureToggleMapper.class);

    FeatureToggle toEntity(FeatureToggleReqDTO dto);
    FeatureToggleResDTO toDTO(FeatureToggle featureToggle);
}
