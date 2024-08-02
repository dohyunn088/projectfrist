package com.green.greenGotell.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NoticeDetailDTO {
		
		private long no;
		private String title;
		private String content;
		private String division;	//구분-"전체","영화관"
		private boolean fixed;	//고정여부
		private LocalDateTime createdAt;	//최초생성일
		private LocalDateTime updatedAt;	//최종수정일

	}
