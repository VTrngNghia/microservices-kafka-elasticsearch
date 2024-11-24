package com.microservices.demo.elastic.querier;

import com.microservices.demo.elastic.common.model.IndexModel;

import java.util.List;

public interface ElasticQuerier<T extends IndexModel> {

	T getById(String id);

	List<T> getByText(String text);

	List<T> getAll();
}
