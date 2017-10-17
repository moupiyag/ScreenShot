/**
 * 
 */
package com.detectify.service;

import com.detectify.config.ConfigReader;
import com.detectify.model.KafkaMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Moupiya
 *
 */
public class RequestProducer {
	
	public static void produceRequest(final List<String> urls)
	{
		Properties props = new Properties();
		 props.put("bootstrap.servers", ConfigReader.getInstance().getString(ConfigReader.KAFKA_BOOTSTRAP_SERVER));
		 props.put("acks", "all");
		 props.put("retries", ConfigReader.getInstance().getInteger(ConfigReader.KAFKA_PRODUCER_RETRIES));
		 props.put("batch.size", ConfigReader.getInstance().getInteger(ConfigReader.KAFKA_PRODUCER_BATCH_SIZE));
		 props.put("linger.ms", ConfigReader.getInstance().getString(ConfigReader.KAFKA_PRODUCER_LINGER));
		 props.put("buffer.memory", ConfigReader.getInstance().getString(ConfigReader.KAFKA_PRODUCER_BUFFER_MEMORY));
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "com.detectify.util.JsonEncoder");

		 final KafkaMessage kafkaMessage = new KafkaMessage();
		 kafkaMessage.setUrls(urls);
		 kafkaMessage.setUuid(UUID.randomUUID().toString());
		 Producer<String, KafkaMessage> producer = new KafkaProducer<String, KafkaMessage>(props);
		 producer.send(new ProducerRecord<String, KafkaMessage>(ConfigReader.getInstance().getString(ConfigReader.KAFKA_SCREENSHOT_TOPIC),UUID.randomUUID().toString(), kafkaMessage));

		 producer.close();
	}
	
}
