/**
 * 
 */
package com.detectify.service;

import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author Moupiya
 *
 */
public class RequestProducer {
	
	public void produceRequest(final String url)
	{
		Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 //Producer<String, String> producer = new KafkaProducer<String, String>(props);
		 final KafkaMessage kafkaMessage = new KafkaMessage();
		 kafkaMessage.setUrl(url);
		 kafkaMessage.setUuid(UUID.randomUUID().toString());
		 Producer<String, KafkaMessage> producer = new KafkaProducer<String, KafkaMessage>(props);
		 for (int i = 25; i < 30; i++)
			 producer.send(new ProducerRecord<String, KafkaMessage>("test",UUID.randomUUID().toString(), kafkaMessage));

		 producer.close();
	}
	
	public static void main(String args[])
	{
		RequestProducer requestProducer = new RequestProducer();
		requestProducer.produceRequest("https://www.google.com");
	}
}
