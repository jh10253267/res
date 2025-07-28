package com.studioreservation.global.response;

import com.studioreservation.global.request.PageRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
	@Schema(description = "요청 페이지", example = "요청 페이지")
	private int page;
	@Schema(description = "한번에 요청할 데이터 개수", example = "한번에 요청할 데이터 개수")
	private int size;
	@Schema(description = "총 데이터 개수", example = "총 데이터 개수")
	private long total;
	@Schema(description = "시작 페이지", example = "시작 페이지")
	private int start;
	@Schema(description = "마지막 페이지", example = "마지막 페이지")
	private int end;
	@Schema(description = "한 화면에 페이지 10개씩 보여주기 위함", example = "이전 페이지 그룹이 있는지")
	private boolean prev;
	@Schema(description = "한 화면에 페이지 10개씩 보여주기 위함", example = "다음 페이지 그룹이 있는지")
	private boolean next;
	@Schema(description = "응답 데이터", example = "응답 데이터")
	private List<E> dtoList;

	@Builder(builderMethodName = "withAll")
	public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, long total) {
		if(total <= 0) {
			return;
		}

		this.page = pageRequestDTO.getPage();
		this.size = pageRequestDTO.getSize();
		this.total = total;
		this.dtoList = dtoList;

		this.end = (int)Math.ceil((this.page / 10.0)) * 10;
		this.start = this.end - 9;

		int last = (int)(Math.ceil((total/(double)size)));
		this.end = end > last ? last: end;
		this.prev = this.start > 1;
		this.next = total > this.end * this.size;
	}
}