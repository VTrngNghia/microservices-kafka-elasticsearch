package com.microservices.demo.twittertokafka.exception;

public class TwitterToKafkaServiceException extends RuntimeException {

	public TwitterToKafkaServiceException() {
		super();
	}

	public TwitterToKafkaServiceException(String message) {
		super(message);
	}

	public TwitterToKafkaServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
