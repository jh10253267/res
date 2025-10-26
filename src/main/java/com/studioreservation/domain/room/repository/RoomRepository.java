package com.studioreservation.domain.room.repository;

import java.util.List;
import java.util.Optional;

import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studioreservation.domain.room.entity.Room;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    default Room findSingleEntity(Long cd) {
		return findById(cd).orElseThrow(() ->
				new StudioException(ErrorCode.NO_SUCH_ROOM));
	}

    @Query("SELECT r FROM Room r JOIN FETCH r.roomInfos")
    List<Room> findAllByOrderByOrderIndexAsc();
}
