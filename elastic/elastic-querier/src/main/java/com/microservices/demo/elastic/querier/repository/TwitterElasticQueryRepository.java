package com.microservices.demo.elastic.querier.repository;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwitterElasticQueryRepository extends ElasticsearchRepository<ElasticTwitterStatus, String> {
	List<ElasticTwitterStatus> findByText(String text);
}
