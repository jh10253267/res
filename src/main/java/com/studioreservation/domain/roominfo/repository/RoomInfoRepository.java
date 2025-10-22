package com.studioreservation.domain.roominfo.repository;

import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.domain.roominfo.entity.RoomInfo;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoRepository extends JpaRepository<RoomInfo,Long> {
    default RoomInfo findSingleEntity(Long cd) {
        return findById(cd).orElseThrow(() ->
                new StudioException(ErrorCode.NO_SUCH_ROOM));
    }
}
