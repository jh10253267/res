package com.studioreservation.domain.platform.service;

import com.studioreservation.domain.platform.dto.PlatformReqDTO;
import com.studioreservation.domain.platform.dto.PlatformResDTO;
import com.studioreservation.domain.platform.mapper.PlatformMapper;
import com.studioreservation.domain.platform.repository.PlatformRepository;
import com.studioreservation.domain.platform.entity.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepository repository;
    private final PlatformMapper mapper;

    public List<PlatformResDTO> getAllPlatform() {
        return repository.findAll()
                .stream().map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlatformResDTO makePlatform(PlatformReqDTO platformReqDTO) {
        Platform platform = mapper.toEntity(platformReqDTO);

        return mapper.toDTO(repository.save(platform));
    }

    public void deletePlatform(Long cd) {
        repository.deleteById(cd);
    }

    public void updatePlatform(PlatformReqDTO reqDTO, Long cd) {
        Platform platform = repository.findById(cd).orElseThrow();
        platform.update(reqDTO);
    }
}
