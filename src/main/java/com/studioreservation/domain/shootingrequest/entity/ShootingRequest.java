package com.studioreservation.domain.shootingrequest.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.studioreservation.domain.shootingrequest.enums.ShootingTyp;
import com.studioreservation.domain.studiofile.entity.StudioFile;
import com.studioreservation.global.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShootingRequest extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;

	@Enumerated(EnumType.STRING)
	private ShootingTyp shootingTyp;

	private String purpose;


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
}
