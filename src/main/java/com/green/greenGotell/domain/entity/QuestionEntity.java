package com.green.greenGotell.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.green.greenGotell.domain.dto.QuestionDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
@Entity
@Table(name="question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionNo;
    
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String category;
    
    @ManyToOne
	@JoinColumn(name = "parent")
    private QuestionEntity parent;
    
    public QuestionDTO toQuestionDTO() {
    	return QuestionDTO.builder()
    			.questionNo(questionNo).category(category).parent(parent.questionNo)
    			.build();
    }

}
