package com.green.greenGotell.domain.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.DynamicUpdate;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.enums.Standard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicUpdate
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="item") 
public class ItemEntity { //품목리스트 추가 테이블
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	//상품코드
	@Column(nullable = true, columnDefinition = "varchar(50)")
	private String itemCode;
	//상품명
	@Column(nullable = true, columnDefinition = "varchar(50)")
	private String name;
	//공급자
	@Column(nullable = true, columnDefinition = "varchar(50)")
	private String itemSource;
	//가격
	@Column(nullable = true, columnDefinition = "bigint")
	private Long itemMoney;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")////물리DB FK는 다테이블에 적용됨
    private CategoryEntity category;
	
	
	//수량단위
	@Enumerated(EnumType.STRING)
	private Standard standard;
	
	public ItemDTO toItemDTO() {
		return ItemDTO.builder()
				.name(name)
				.itemCode(itemCode)
				.itemSource(itemSource)
				.itemMoney(itemMoney)
				.standard(standard)
				.categoryId(category.getId())
				.build();
	}
	
	
	
	
}
