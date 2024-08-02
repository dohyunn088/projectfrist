package com.green.greenGotell.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	
	EMP(1,"사원"),
	DIR(2,"부장"),
	CEO(3,"총지배인");
	
	private final int number;
	private final String roleName;
}
