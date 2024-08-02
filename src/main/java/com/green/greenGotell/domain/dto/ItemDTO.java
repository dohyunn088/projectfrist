package com.green.greenGotell.domain.dto;

import com.green.greenGotell.domain.entity.CategoryEntity;
import com.green.greenGotell.domain.entity.ItemEntity;
import com.green.greenGotell.domain.enums.Standard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	private Long id;
	// 상품코드
	private String itemCode;
	// 상품명
	private String name;
	// 규격
	private Standard standard;
	// 공급자
	private String itemSource;
	// 가격
	private Long itemMoney;
	private Long categoryId;
	private String cateogoryName;


	public ItemEntity toEntity() {
		return ItemEntity.builder().itemCode(itemCode).name(name).itemSource(itemSource).itemMoney(itemMoney)
				.standard(standard).category(CategoryEntity.builder().id(categoryId).build())
				.build();
	}

}
