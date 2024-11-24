package com.microservices.demo.elastic.querier;

public class ElasticQuerierException extends RuntimeException {

	public ElasticQuerierException() {
		super();
	}

	public ElasticQuerierException(String message) {
		super(message);
	}

	public ElasticQuerierException(String message, Throwable t) {
		super(message, t);

	}
}