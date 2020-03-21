package com.singer.kafka;

import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

@Component("producer")
public class Producer {

	private final Log log = LogFactory.getLog(Producer.class);

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

		//Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, message),
			//	new KafkaCallback());

	}
}
