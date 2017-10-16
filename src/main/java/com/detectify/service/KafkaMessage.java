package com.detectify.service;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by moupiya on 10/16/17.
 */
@Getter
@Setter
public class KafkaMessage {
    private String url;
    private String uuid;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}