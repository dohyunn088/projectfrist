package com.green.greenGotell.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Standard {
	EACH("EA"),
	PACKAGE("PK"),
	BOX("BOX"),
	GRAM("GR"),
	KG("KG");
	
	private final String displayName;
}
