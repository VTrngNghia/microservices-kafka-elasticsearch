package com.microservices.demo.elastic.querier;

import com.microservices.demo.elastic.common.model.IndexModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ElasticQuerier<T extends IndexModel> {

	Mono<T> getById(String id);

	Mono<List<T>> getByText(String text);

	Mono<List<T>> getAll();
}
