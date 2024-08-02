package com.green.greenGotell.domain.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ItemSearchDTO {
	
	private String name;
	private List<String> categoryId;
	
	public String maxCategoryId() {
		if (categoryId == null) {
			return "0";
		}
		Optional<String> max = categoryId.stream().max(String::compareTo);
		return max.orElse("0");
	}
	
	public boolean isEmpty() {
		return maxCategoryId().isEmpty() && (name == null || name.trim().isEmpty());
	}
}