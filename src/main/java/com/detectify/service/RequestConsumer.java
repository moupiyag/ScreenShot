/**
 * 
 */
package com.detectify.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.detectify.util.ContextProvider;
import com.detectify.util.Json;

/**
 * @author Moupiya
 *
 */
public class RequestConsumer {

	private static KafkaConsumer<String, Byte[]> consumer;

	public static void start()
	{
		//		Properties props = new Properties();
		//	     props.put("bootstrap.servers", "localhost:9092");
		//	     props.put("group.id", "test");
		//	     props.put("enable.auto.commit", "false");
		//	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//	     KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		//	     consumer.subscribe(Arrays.asList("test"));
		//	     final int minBatchSize = 200;
		//	     List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
		//	     while (true) {
		//	         ConsumerRecords<String, String> records = consumer.poll(100);
		//	         for (ConsumerRecord<String, String> record : records) {
		//	             buffer.add(record);
		init();
//		final int minBatchSize = 200;
//		List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
		while (true) {
			ConsumerRecords<String, Byte[]> records = consumer.poll(100);
			ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
			for (ConsumerRecord<String, Byte[]> record : records) {
				Json json = Json.copyOf(new String(String.valueOf(record.value())));
				KafkaMessage kafkaMessage = json.cast(KafkaMessage.class);
				//Process kafkaMessage.getUrl();
//				buffer.add(record);
				try {
					screenShotService.takeScreenShots(kafkaMessage.getUrls());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(record.value());
			}
//			if (buffer.size() >= minBatchSize) {
				//	             insertIntoDb(buffer);
				consumer.commitSync();
//				buffer.clear();
//			}
		}
	}

	private static void init() {
		if(consumer == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", "localhost:9092");
			props.put("group.id", "test");
			props.put("enable.auto.commit", "false");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			consumer = new KafkaConsumer<String, Byte[]>(props);
			consumer.subscribe(Arrays.asList("test"));
		}
	}

	public void stop() {
		if(consumer != null) {
			consumer.close();
		}
	}

	public static void main(String args[])
	{
		RequestConsumer requestConsumer = new RequestConsumer();
		requestConsumer.start();
	}

}
