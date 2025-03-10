package com.studioreservation.global.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
	@Builder.Default
	private int page = 1;
	@Builder.Default
	private int size = 10;
	@Builder.Default
	private boolean desc = true;
	@Builder.Default
	private String sortBy = "createdAt";

	public Pageable getPageable(String props) {
		Sort sort = desc ? Sort.by(props).descending() : Sort.by(props).ascending();
		return PageRequest.of(this.page - 1, this.size, sort);
	}
}
