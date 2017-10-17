/**
 * 
 */
package com.detectify.service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.detectify.util.ContextProvider;

/**
 * @author Moupiya
 *
 */
public class RequestProducer {
	
	public static void produceRequest(final List<String> urls)
	{
		Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "com.detectify.util.JsonEncoder");

		 final KafkaMessage kafkaMessage = new KafkaMessage();
		 kafkaMessage.setUrls(urls);
		 kafkaMessage.setUuid(UUID.randomUUID().toString());
		 Producer<String, KafkaMessage> producer = new KafkaProducer<String, KafkaMessage>(props);
		 producer.send(new ProducerRecord<String, KafkaMessage>("test",UUID.randomUUID().toString(), kafkaMessage));

		 producer.close();
	}
	
	public static void main(String args[])
	{
		RequestProducer requestProducer = new RequestProducer();
		requestProducer.produceRequest(Arrays.asList("https://www.google.com"));
	}
}
