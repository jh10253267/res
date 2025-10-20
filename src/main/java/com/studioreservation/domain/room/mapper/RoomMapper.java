package com.studioreservation.domain.room.mapper;

import com.studioreservation.domain.room.dto.RoomAdminResponseDTO;
import com.studioreservation.domain.roominfo.dto.RoomInfoResponseDTO;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.studioreservation.domain.room.dto.RoomResponseDTO;
import com.studioreservation.domain.room.dto.RoomRequestDTO;
import com.studioreservation.domain.room.entity.Room;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomInfoResponseDTO roomInfoToDTO(RoomInfo roomInfo);

    @Mapping(target = "roomInfos", source = "roomInfos")
    RoomAdminResponseDTO toAdminDTO(Room room);

    @Mapping(target = "roomInfos", source = "roomInfos")
    RoomResponseDTO toDTO(Room room);

    List<RoomAdminResponseDTO> toAdminDTOs(List<Room> rooms);

    List<RoomResponseDTO> toDTOs(List<Room> rooms);


    Room toEntity(RoomRequestDTO roomRequestDTO);
}
