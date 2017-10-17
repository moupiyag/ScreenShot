/**
 * 
 */
package com.detectify.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.detectify.util.ContextProvider;
import com.detectify.util.Json;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @author Moupiya
 *
 */
public class RequestConsumer implements Runnable{

	@Getter
	private static KafkaConsumer<String, Byte[]> consumer;

	public void run()
	{
		init();
		while (true) {
			ConsumerRecords<String, Byte[]> records = consumer.poll(100);
			ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
			for (ConsumerRecord<String, Byte[]> record : records) {
				Json json = Json.copyOf(new String(String.valueOf(record.value())));
				KafkaMessage kafkaMessage = json.cast(KafkaMessage.class);
				try {
					screenShotService.takeScreenShots(kafkaMessage.getUrls());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			consumer.commitSync();
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
		requestConsumer.run();
	}

}
