package com.github.springkit.producer.impl;

import javax.annotation.Resource;

import com.github.springkit.config.ProducerConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.github.springkit.producer.IMessageProducerService;
@Service
public class MessageProducerServiceImpl implements IMessageProducerService {
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Override
	public void sendMessage(String msg) {
		this.rabbitTemplate.convertAndSend(ProducerConfig.EXCHANGE,
				ProducerConfig.ROUTINGKEY, msg);
	}

}
