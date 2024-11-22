package com.microservices.demo.kafka.admin;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class KafkaClientException extends RuntimeException {
	private String message;

	private Throwable cause;

	public KafkaClientException(String s) {
		super(s);
	}
}
