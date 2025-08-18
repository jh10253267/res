package com.studioreservation.domain.platform.repository;

import com.studioreservation.domain.platform.entity.Platform;
import com.studioreservation.domain.room.entity.Room;
import com.studioreservation.global.exception.ErrorCode;
import com.studioreservation.global.exception.StudioException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    default Platform findSingleEntity(Long cd) {
        return findById(cd).orElseThrow(() ->
                new StudioException(ErrorCode.NO_SUCH_PLATFORM));
    }

}
