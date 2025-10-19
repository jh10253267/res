package com.studioreservation.domain.roominfo.repository;

import com.studioreservation.domain.roominfo.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoRepository extends JpaRepository<RoomInfo,Long> {
}
