package com.green.greenGotell.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.green.greenGotell.domain.dto.NoticeDetailDTO;
import com.green.greenGotell.domain.dto.NoticeListDTO;
import com.green.greenGotell.domain.dto.NoticeUpdateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@SequenceGenerator(name = "gen_notice",
					sequenceName = "seq+notice", initialValue = 1, allocationSize = 1)

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notice")
@Getter
@Entity
public class NoticeEntity extends BaseEntity { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_notice")
	private long no;
	
	@Column(nullable = false)
	private String title;
	@Column(columnDefinition = "text" ,nullable = false)
	private String content;
	
	private String division;	//구분-"전체","영화관"
	private boolean fixed;		//고정여부
	 
//	@Column(nullable = false)
	@ManyToOne
	//@JoinColumn(name = "empId")//employee(객체변수이름)_id(pk)
	private EmployeesEntity employee; //작성자위한 객체
	
	
	//편의메소드 매핑하기위한 메소드
	public NoticeListDTO toNoticeListDTO() {
		return NoticeListDTO.builder()
				.no(no)
				.title(title)
				.division(division)
				.fixed(fixed)
				.name(employee != null ? employee.getName() : null)
				.updatedAt(getUpdatedAt())
				.build();
	}

	//상세페이지 dto
	public NoticeDetailDTO toNoticeDetailDTO() {
		return NoticeDetailDTO.builder()
				.no(no)
				.title(title)
				.content(content)
				.division(division)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.fixed(fixed)
				.build();
	}

	//제목 or 내용 수정 dto
	public NoticeEntity update(NoticeUpdateDTO dto) {
		this.title=dto.getTitle();
		this.content=dto.getContent();
		this.fixed=dto.isFixed();
		this.division=dto.getDivision();
		return this;
		
	}
	
	
}
