package com.green.greenGotell.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.greenGotell.domain.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer>{

	List<QuestionEntity> findAllByParent_questionNo(int parent);
}
