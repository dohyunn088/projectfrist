package com.green.greenGotell.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
		//클라이언트와 서버간의 메시지 교환 기술. 
	}
}