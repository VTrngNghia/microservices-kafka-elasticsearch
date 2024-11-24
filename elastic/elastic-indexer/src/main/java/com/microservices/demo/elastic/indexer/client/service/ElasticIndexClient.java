package com.microservices.demo.elastic.indexer.client.service;

import com.microservices.demo.elastic.common.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
	/**
	 * @param documents List of documents to save
	 * @return List of saved documents ids
	 */
	List<String> save(List<T> documents);
}
