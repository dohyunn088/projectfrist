package com.green.greenGotell.controller;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.greenGotell.domain.dto.HelloDTO;
import com.green.greenGotell.domain.dto.QuestionDTO;
import com.green.greenGotell.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatBotController {

	private final SimpMessagingTemplate messagingTemplate;
    private final ChatBotService chatBotService;

    
    
    @MessageMapping("/hello")
    public void hello(HelloDTO dto) {
    	System.out.println(">>>:"+dto);
        long key = dto.getKey();
        String pattern = "안녕하세요! 원하시는 질문을 눌러주세요";
        messagingTemplate.convertAndSend("/topic/bot/" + key, MessageFormat.format(pattern, dto.getName())
				);
	}
    
 // 질문 데이터를 가져오는 REST API 엔드포인트 추가
    @GetMapping("/api/questions")
    public List<QuestionDTO> getQuestions(@RequestParam int parent) {
        return chatBotService.getCategoryByType(parent);
    }
    
}