package com.green.greenGotell.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.green.greenGotell.domain.dto.QuestionDTO;
import com.green.greenGotell.domain.entity.QuestionEntity;
import com.green.greenGotell.domain.repository.QuestionRepository;
import com.green.greenGotell.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotServiceProcess implements ChatBotService {
	
	private final QuestionRepository questionRepository;
	
	@Override
	public List<QuestionDTO> getCategoryByType(int parent) {
		List<QuestionEntity> bot = questionRepository.findAllByParent_questionNo(parent);
		return bot.stream()
					.map(QuestionEntity::toQuestionDTO)
					.collect(Collectors.toList());
	}
}
