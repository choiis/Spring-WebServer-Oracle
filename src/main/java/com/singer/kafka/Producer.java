package com.singer.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("producer")
public class Producer {

	private final Log log = LogFactory.getLog(Producer.class);

	@Value("#{kafka['bootstrap.servers']}")
	private String kafkaServer;

	@Value("#{kafka['acks']}")
	private String acks;

	@Value("#{kafka['retries']}")
	private String retries;

	@Value("#{kafka['key.serializer']}")
	private String keySerializer;

	@Value("#{kafka['value.serializer']}")
	private String valueSerializer;

	@Value("#{kafka['topic']}")
	private String topic;

	KafkaProducer<String, String> producer;

	@PostConstruct
	private void init() {
		Properties config = new Properties();
		config.put("bootstrap.servers", kafkaServer);
		config.put("key.serializer", keySerializer);
		config.put("value.serializer", valueSerializer);
		config.put("acks", acks);
		config.put("retries", retries);

		producer = new KafkaProducer<String, String>(config);
	}

	public void send(String message) {
		Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, message),
				new KafkaCallback());
		try {
			RecordMetadata metadata = future.get();
			log.info("kafka send metadata " + metadata.topic());
		} catch (InterruptedException | ExecutionException e) {
			log.error(e, e);
		}

	}
}
