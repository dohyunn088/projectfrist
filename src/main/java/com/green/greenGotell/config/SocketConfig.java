package com.green.greenGotell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer{

	
	/*
	 * @Override public void registerStompEndpoints(StompEndpointRegistry registry)
	 * {
	 * 
	 * registry.addEndpoint("/ws-green-bot").withSockJS();
	 * 
	 * }
	 * 
	 * @Override public void configureMessageBroker(MessageBrokerRegistry registry)
	 * { registry.setApplicationDestinationPrefixes("/bot");
	 * 
	 * registry.enableSimpleBroker("/topic"); }
	 */
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

	/**
	 * 
	 * 
	 * 	config.setApplicationDestinationPrefixes("/app");
	 * 
	 * 		---js에서 
	 * 		var data={
	 *			key: key,
	 *			content: "hello",
	 *			name: "guest" //principle.getName 이름의 값으로 넣으면 될 듯..?
	 *		}
	 *		//접속하자마자 연결시도
	 *		client.send("/app/hello",{},JSON.stringify(data));
	 *		---
	 *
	 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	
    	//메세지수신시 필요한
        config.enableSimpleBroker("/topic");
        
        
        config.setApplicationDestinationPrefixes("/app");
    }
}
