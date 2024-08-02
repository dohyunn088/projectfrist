package com.green.greenGotell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
	
	@GetMapping("/chat")
	public String chat() {
		return "/views/chat/chat";
	}
	
	@GetMapping("/chat2")
	public String chat2() {
		return "/views/chat/chat2";
	}
	
	
}
