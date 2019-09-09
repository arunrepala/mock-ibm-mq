package com.tongyy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mockrunner.mock.jms.MockQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MockRunnerJMSConfiguration.class)
public class QueueAndTopicTest {

	@Autowired
	private JmsTemplate queueTemplate;
	@Autowired
	private JmsTemplate topicTemplate;

	private String queue = "ONE.REQ";
	private String topic = "ONE/TOPIC";
	@Autowired
	private MockQueue mockQueue;

	@Test
	public void sendMessageToQueue() {

		queueTemplate.convertAndSend(mockQueue, "hello world");
	}

	@Test
	public void receiveMessageFromQueue() {

		queueTemplate.receiveAndConvert(mockQueue);
	}

	@Test
	public void sendMessageToQueueWithSelector() {

		queueTemplate.convertAndSend(mockQueue, "hello world", (MessagePostProcessor) (message) -> {
			{
				message.setStringProperty("name", "tony");
				return message;
			}
		});
	}

	@Test
	public void receiveMessageFromQueueWithSelector() {

		queueTemplate.receiveSelected(mockQueue, "name='tony'");
	}

	@Test
	public void publishMessageToTopic() {

		topicTemplate.convertAndSend(topic, "hello world");
	}

}
