package com.green.greenGotell.service;

import java.util.List;

import com.green.greenGotell.domain.dto.QuestionDTO;

public interface ChatBotService {

	List<QuestionDTO> getCategoryByType(int parent);
}
