package com.microservices.demo.elastic.index.client.service;

import com.microservices.demo.elastic.model.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
	/**
	 * @param documents List of documents to save
	 * @return List of saved documents ids
	 */
	List<String> save(List<T> documents);
}
