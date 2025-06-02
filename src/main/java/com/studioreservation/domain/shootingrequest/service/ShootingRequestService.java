package com.studioreservation.domain.shootingrequest.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.studioreservation.domain.reservation.entity.ReservationHistory;
import com.studioreservation.domain.shootingrequest.dto.ShootingReqResponseDTO;
import com.studioreservation.domain.shootingrequest.dto.ShootingRequestDTO;
import com.studioreservation.domain.shootingrequest.entity.ShootingRequest;
import com.studioreservation.domain.shootingrequest.mapper.ShootingRequestMapper;
import com.studioreservation.domain.shootingrequest.repository.ShootingRequestRepository;
import com.studioreservation.global.request.PageRequestDTO;
import com.studioreservation.global.response.PageResponseDTO;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShootingRequestService {
	private final ShootingRequestRepository repository;
	private final ShootingRequestMapper mapper;

	public ShootingReqResponseDTO shootingRequest(ShootingRequestDTO shootingRequestDTO) {
		ShootingRequest shootingRequest = mapper.toEntity(shootingRequestDTO);
		ShootingRequest savedShootingRequest = repository.save(shootingRequest);

		return mapper.toDTO(savedShootingRequest);
	}

	public PageResponseDTO<ShootingReqResponseDTO> getAllShootingRequests(PageRequestDTO requestDTO) {
		Page<ShootingReqResponseDTO> result = repository
			.findAll(requestDTO.getPageable(requestDTO.getSortBy()))
			.map(mapper::toDTO);

		return PageResponseDTO.<ShootingReqResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(requestDTO)
			.total(result.getTotalElements())
			.build();
	}
	public PageResponseDTO<ShootingReqResponseDTO> getAllShootingRequestsByStrtDt(PageRequestDTO requestDTO) {
		Specification<ShootingRequest> spec = Specification.where(null);

		if (requestDTO.getStrtDt() != null) {
			spec = spec.and((root, query, cb) -> {
					Path<Timestamp> strtDt = root.get("strtDt");

					Predicate p = cb.conjunction();
					if (requestDTO.getStrtDt() != null) {
						p = cb.and(p, cb.lessThanOrEqualTo(strtDt, requestDTO.getEndDt()));
					}
					return p;
			});
		}

		Page<ShootingReqResponseDTO> result = repository
			.findAll(spec, requestDTO.getPageable(requestDTO.getSortBy()))
			.map(mapper::toDTO);

		return PageResponseDTO.<ShootingReqResponseDTO>withAll()
			.data(result.getContent())
			.pageRequestDTO(requestDTO)
			.total(result.getTotalElements())
			.build();
	}

	public ShootingReqResponseDTO getShootingRequest(Long sn) {
		ShootingRequest shootingRequest = repository.findById(sn).orElseThrow();

		return mapper.toDTO(shootingRequest);
	}
}
