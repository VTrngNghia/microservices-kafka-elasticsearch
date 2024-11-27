package com.microservices.demo.elastic.querier.repository;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TwitterElasticQueryRepository extends ReactiveElasticsearchRepository<ElasticTwitterStatus, String> {
	Flux<ElasticTwitterStatus> findByText(String text);
}
