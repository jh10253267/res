package com.studioreservation.domain.shootingrequest.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.studioreservation.domain.purpose.entity.Purpose;
import com.studioreservation.domain.shootingrequest.dto.ShootingRequestDTO;
import com.studioreservation.domain.shootingrequest.enums.ShootingTyp;
import com.studioreservation.domain.studiofile.entity.StudioFile;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShootingRequest extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;

	@Enumerated(EnumType.STRING)
	private ShootingTyp shootingTyp;

    private String username;

    private String phone;

    private String email;

	private Timestamp strtDt;

	private String quantity;

	@Column(columnDefinition = "text")
	private String description;

	private String refLink;

	@OneToMany(mappedBy = "shootingRequest", fetch = FetchType.LAZY)
	@Builder.Default
	private Set<StudioFile> imageSet = new HashSet<>();

	private boolean policyConfirmed;

	private boolean newsConfirmed;

	@ManyToOne
	@Setter
    private Purpose purpose;

	public static ShootingRequest buildStudioRequest(ShootingRequestDTO requestDTO, Purpose purpose) {
		ShootingRequest shootingRequest = ShootingRequest.builder()
				.shootingTyp(requestDTO.getShootingTyp())
				.description(requestDTO.getDescription())
                .username(requestDTO.getUsername())
                .phone(requestDTO.getPhone())
                .email(requestDTO.getEmail())
				.purpose(purpose)
                .strtDt(requestDTO.getStrtDt())
                .quantity(requestDTO.getQuantity())
				.policyConfirmed(requestDTO.isPolicyConfirmed())
				.newsConfirmed(requestDTO.isNewsConfirmed())
				.build();

		if (requestDTO.getFileNames() != null) {
			requestDTO.getFileNames().forEach(fileName -> {
				String[] arr = fileName.getSavedFileName().split("_");
				shootingRequest.addImage(arr[0], arr[1], fileName.getSavedFileUrl());
			});
		}

		return shootingRequest;
	}

	public void addImage(String uuid, String fileName, String savedFileUrl) {
		StudioFile studioFile = StudioFile.builder()
				.uuid(uuid)
				.fileName(fileName)
				.savedFileUrl(savedFileUrl)
				.shootingRequest(this)
				.build();

		this.imageSet.add(studioFile);
	}

	public void clearImages() {
		imageSet.forEach(studioFile -> {
			studioFile.changeRequest(null);
			this.imageSet.clear();
		});
	}

}
