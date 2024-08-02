package com.green.greenGotell.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeStatus {
	

	EMPLOYED(1,"재직"),
	LEAVE_OF_ABSENCE(2,"휴직"),
	RESIGNED(3,"퇴사");
	
	
	private final int number;
	private final String KoName;

}
