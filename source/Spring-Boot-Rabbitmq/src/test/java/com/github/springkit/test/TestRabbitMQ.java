package com.github.springkit.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springkit.StartSpringBootMain;
import com.github.springkit.producer.IMessageProducerService;

@SpringBootTest(classes = StartSpringBootMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestRabbitMQ {
	@Resource
	private IMessageProducerService messageProducer;
	@Test
	public void testSend() throws Exception {
		for (int x = 0; x < 100; x++) {
			this.messageProducer.sendMessage("alex - " + x);
		}
	}
}
