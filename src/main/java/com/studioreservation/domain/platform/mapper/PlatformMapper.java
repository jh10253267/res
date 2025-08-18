package com.studioreservation.domain.platform.mapper;

import com.studioreservation.domain.platform.dto.PlatformReqDTO;
import com.studioreservation.domain.platform.dto.PlatformResDTO;
import com.studioreservation.domain.platform.entity.Platform;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlatformMapper {
    PlatformMapper INSTANCE = Mappers.getMapper(PlatformMapper.class);

    PlatformResDTO toDTO(Platform platform);

    Platform toEntity(PlatformReqDTO platformReqDTO);

}
