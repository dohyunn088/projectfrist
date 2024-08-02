package com.green.greenGotell.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

	private int questionNo;
	private String category;
	private Integer parent; // 변경: 부모가 없는 경우를 위해 Integer로 변경
}
