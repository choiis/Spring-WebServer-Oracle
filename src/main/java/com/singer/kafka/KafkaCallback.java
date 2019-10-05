package com.singer.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.util.ObjectUtils;

class KafkaCallback implements Callback {

	private final Log log = LogFactory.getLog(KafkaCallback.class);

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		// TODO Auto-generated method stub
		if (!ObjectUtils.isEmpty(metadata)) {
			log.info("Partition: {" + metadata.partition() + "}, Offset: {" + metadata.offset() + "}");
		} else {
			log.error("KafkaCallback - Exception");
		}
	}

}
