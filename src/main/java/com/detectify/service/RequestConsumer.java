/**
 * 
 */
package com.detectify.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import lombok.Getter;
import com.detectify.config.ConfigReader;
import com.detectify.model.KafkaMessage;
import com.detectify.util.ContextProvider;
import com.detectify.util.Json;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Moupiya
 *
 */
public class RequestConsumer implements Runnable{

	@Getter
	private static KafkaConsumer<String, Byte[]> consumer;

	public void run()
	{
		System.out.println("Starting Kafka Consumer...................");
		init();
		while (true) {
			ConsumerRecords<String, Byte[]> records = consumer.poll(100);
			ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
			for (ConsumerRecord<String, Byte[]> record : records) {
				System.out.println("Found Consumer record : " + record.key());
				Json json = Json.copyOf(new String(String.valueOf(record.value())));
				KafkaMessage kafkaMessage = json.cast(KafkaMessage.class);
				try {
					screenShotService.takeScreenShots(kafkaMessage.getUrls());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			consumer.commitSync();
		}
	}

	private static void init() {
		if(consumer == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", ConfigReader.getInstance().getString(ConfigReader.KAFKA_BOOTSTRAP_SERVER));
			props.put("group.id", ConfigReader.getInstance().getString(ConfigReader.KAFKA_GROUP_ID));
			props.put("enable.auto.commit", ConfigReader.getInstance().getString(ConfigReader.KAFKA_ENABLE_AUTO_COMMIT));
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			consumer = new KafkaConsumer<String, Byte[]>(props);
			consumer.subscribe(Arrays.asList(ConfigReader.getInstance().getString(ConfigReader.KAFKA_SCREENSHOT_TOPIC)));
		}
	}

	public void stop() {
		if(consumer != null) {
			consumer.close();
		}
	}

}
