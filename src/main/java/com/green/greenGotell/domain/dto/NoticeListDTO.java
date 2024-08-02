package com.green.greenGotell.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class NoticeListDTO{
	
	private long no;
	private String title;
	private String division;	//구분-"전체","영화관"
	private boolean fixed;		//고정여부
	private LocalDateTime updatedAt;
	
	private String name;//작성자의이름

}
