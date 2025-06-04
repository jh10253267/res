package com.studioreservation.domain.shootingrequest.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.studioreservation.domain.purpose.entity.Purpose;
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
	Purpose purpose;
}
