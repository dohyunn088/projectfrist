package com.green.greenGotell.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceStatus {
	
	LATE(1,"지각"),
	EARLY_DEPARTURE(2,"조퇴"),
	NORMAL(3,"정상"),
	LATE_AND_EARLY_LEAVE(8,"지각/조퇴");
	
	private final int number;
	private final String KoName;


}
