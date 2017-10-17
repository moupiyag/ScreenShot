package com.detectify.service;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by moupiya on 10/16/17.
 */
@Getter
@Setter
public class KafkaMessage {
    private String urls;
    private String uuid;
	public String getUrls() {
		return urls;
	}
	public void setUrl(String urls) {
		this.urls = urls;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}