/**
 * 
 */
package com.detectify.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Moupiya
 *
 */
public class RequestConsumer {
	
	public void consumeRequest()
	{
		Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "false");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test"));
	     final int minBatchSize = 200;
	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records) {
	             buffer.add(record);
	             System.out.println(record.value());
	         }
	         if (buffer.size() >= minBatchSize) {
//	             insertIntoDb(buffer);
	             consumer.commitSync();
	             buffer.clear();
	         }
	     }
	}
	
	public static void main(String args[])
	{
		RequestConsumer requestConsumer = new RequestConsumer();
		requestConsumer.consumeRequest();
	}

}
