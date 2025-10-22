package com.studioreservation.domain.roominfo.mapper;

import com.studioreservation.domain.roominfo.dto.RoomInfoAdminResponseDTO;
import com.studioreservation.domain.roominfo.dto.RoomInfoRequestDTO;
import com.studioreservation.domain.roominfo.dto.RoomInfoResponseDTO;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomInfoMapper {
    RoomInfo toEntity(RoomInfoRequestDTO requestDTO);
    RoomInfoResponseDTO toDTO(RoomInfo roomInfo);
    RoomInfoAdminResponseDTO toAdminDTO(RoomInfo roomInfo);
}
