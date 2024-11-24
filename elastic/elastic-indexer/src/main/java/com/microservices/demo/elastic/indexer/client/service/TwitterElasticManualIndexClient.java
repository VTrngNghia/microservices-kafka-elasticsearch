package com.microservices.demo.elastic.indexer.client.service;

import com.microservices.demo.appconfig.ElasticConfigValues;
import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.indexer.client.ElasticIndexUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "false")
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticManualIndexClient implements ElasticIndexClient<ElasticTwitterStatus> {
	private final ElasticConfigValues elasticConfigValues;

	private final ElasticsearchOperations elasticsearchOperations;

	private final ElasticIndexUtil<ElasticTwitterStatus> elasticIndexUtil;

	@Override
	public List<String> save(List<ElasticTwitterStatus> documents) {
		List<IndexQuery> indexQueries = elasticIndexUtil
			.getIndexQueries(documents);
		List<String> ids = elasticsearchOperations.bulkIndex(
			indexQueries,
			IndexCoordinates.of(elasticConfigValues.getIndexName())
		).stream().map(IndexedObjectInformation::id).toList();
		log.info(
			"Indexed docs type={} ids={}",
			ElasticTwitterStatus.class.getName(),
			ids
		);
		return ids;
	}
}
