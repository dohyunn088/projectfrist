package com.green.greenGotell.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
	
	FRONT_OFFICE(1,"프론트오피스부"),
	FOOD_AND_BEVERAGE(2,"식음료부"),
	FACILITY_MANAGEMENT(3,"시설관리부"),
	SECURITY(4,"보안부"),
	HUMAN_RESOURCES(5,"인사부"),
	SUPPORT(6,"지원부"),
	MARKETING(7,"마케팅부"),
	EXECUTIVE(8,"최고경영진");
	
	private final int number;
	private final String KoName;

}
