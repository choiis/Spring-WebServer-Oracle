package com.singer.kafka;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Component("producer")
public class Producer {

	@Resource(name = "kafkaProperties")
	private Properties config;

	KafkaProducer<String, String> producer;

	private String topic;

	@PostConstruct
	private void init() {
		topic = config.getProperty("topic");
		producer = new KafkaProducer<String, String>(config);
	}

	public void send(String message) {

		producer.send(new ProducerRecord<String, String>(topic, message));
	}
}
