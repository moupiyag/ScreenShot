/**
 * 
 */
package com.detectify.service;

/**
 * @author Moupiya
 *
 */
public class MessageServiceImpl implements MessageService {
	
	RequestProducer requestProducer;

	public void setRequestProducer(RequestProducer requestProducer) {
		this.requestProducer = requestProducer;
	}

	@Override
	public void sendMessage(String urls) {
		requestProducer.produceRequest(urls);	
	}

}
