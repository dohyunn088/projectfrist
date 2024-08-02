package com.green.greenGotell.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeUpdateDTO {

	private String division;
	private String title;
	private String content;
	private boolean fixed;
}
