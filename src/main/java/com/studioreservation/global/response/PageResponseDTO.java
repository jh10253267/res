package com.studioreservation.global.response;

import com.studioreservation.global.request.PageRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
	private int page;
	private int size;
	private long total;
	private int start;
	private int end;
	private boolean prev;
	private boolean next;
	private List<E> data;

	@Builder(builderMethodName = "withAll")
	public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> data, long total) {
		if(total <= 0) {
			return;
		}

		this.page = pageRequestDTO.getPage();
		this.size = pageRequestDTO.getSize();
		this.total = total;
		this.data = data;

		this.end = (int)Math.ceil((this.page / 10.0)) * 10;
		this.start = this.end - 9;

		int last = (int)(Math.ceil((total/(double)size)));
		this.end = end > last ? last: end;
		this.prev = this.start > 1;
		this.next = total > this.end * this.size;
	}
}