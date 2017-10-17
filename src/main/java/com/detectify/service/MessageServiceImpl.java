/**
 * 
 */
package com.detectify.service;

import java.util.List;

/**
 * @author Moupiya
 *
 */
public class MessageServiceImpl implements MessageService {
	
	RequestProducer requestProducer;

	public void setRequestProducer(RequestProducer requestProducer) {
		this.requestProducer = requestProducer;
	}

	public void sendMessage(List<String> urls) {
		requestProducer.produceRequest(urls);	
	}

}
