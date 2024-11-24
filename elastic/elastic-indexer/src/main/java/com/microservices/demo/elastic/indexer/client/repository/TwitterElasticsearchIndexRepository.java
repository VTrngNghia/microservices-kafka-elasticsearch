package com.microservices.demo.elastic.indexer.client.repository;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticsearchIndexRepository extends ElasticsearchRepository<ElasticTwitterStatus, String> {
}
