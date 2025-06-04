package com.studioreservation.domain.purpose.service;

import com.studioreservation.domain.purpose.dto.PurposeRequestDTO;
import com.studioreservation.domain.purpose.dto.PurposeResponseDTO;
import com.studioreservation.domain.purpose.entity.Purpose;
import com.studioreservation.domain.purpose.mapper.PurposeMapper;
import com.studioreservation.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurposeService {
    private final PurposeRepository repository;
    private final PurposeMapper mapper;

    public List<PurposeResponseDTO> getAllPurposes() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "cd")).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public PurposeResponseDTO createPurpose(PurposeRequestDTO purposeRequestDTO) {
        Purpose purpose = mapper.toEntity(purposeRequestDTO);

        return mapper.toDTO(repository.save(purpose));
    }

    @Transactional
    public PurposeResponseDTO editPurpose(PurposeRequestDTO requestDTO, Long cd) {
        Purpose purpose = repository.findById(cd).orElseThrow();
        purpose.updatePurpose(requestDTO);

        return mapper.toDTO(purpose);
    }

}
