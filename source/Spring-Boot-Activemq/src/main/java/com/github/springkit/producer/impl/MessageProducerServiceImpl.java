package com.github.springkit.producer.impl;

import javax.annotation.Resource;
import javax.jms.Queue;

import com.github.springkit.producer.IMessageProducerService;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerServiceImpl implements IMessageProducerService {
	@Resource
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Resource
	private Queue queue;
	@Override
	public void sendMessage(String msg) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
	}

}
