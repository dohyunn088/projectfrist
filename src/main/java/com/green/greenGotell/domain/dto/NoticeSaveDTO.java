package com.green.greenGotell.domain.dto;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.entity.NoticeEntity;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter	//Controller에서 메소드바인딩(파라미터 매핑)시 setter메소드가 있어야함.
public class NoticeSaveDTO{
	
	private String division;	//구분-"전체","영화관"
	private boolean fixed;		//고정여부
	private String title;		//제목
	private String content;		//내용
	
	//formdata->dto->entity
	public NoticeEntity toEntity(EmployeesEntity employeesEntity) {
		return NoticeEntity.builder()
				.division(division)
				.fixed(fixed)
				.title(title)
				.content(content)
				.employee(employeesEntity)
				.build();
	}

	public NoticeEntity toEntity(Long id) {
		
		return NoticeEntity.builder()
				.division(division)
				.fixed(fixed)
				.title(title)
				.content(content)
				.employee(EmployeesEntity.builder().id(id).build())
				.build();
	}

}
