package com.studioreservation.domain.purpose.mapper;


import com.studioreservation.domain.purpose.dto.PurposeRequestDTO;
import com.studioreservation.domain.purpose.dto.PurposeResponseDTO;
import com.studioreservation.domain.purpose.entity.Purpose;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurposeMapper {
    PurposeMapper INSTANCE = Mappers.getMapper(PurposeMapper.class);

    PurposeResponseDTO toDTO(Purpose purpose);
    Purpose toEntity(PurposeRequestDTO purposeRequestDTO);
}
