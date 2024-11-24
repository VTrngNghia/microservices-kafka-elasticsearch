package com.microservices.demo.elastic.index.client.service;

import com.microservices.demo.appconfig.ElasticConfigValues;
import com.microservices.demo.elastic.index.client.ElasticIndexUtil;
import com.microservices.demo.elastic.model.index.TwitterIndexModel;
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
public class TwitterElasticManualIndexClient implements ElasticIndexClient<TwitterIndexModel> {
	private final ElasticConfigValues elasticConfigValues;

	private final ElasticsearchOperations elasticsearchOperations;

	private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

	@Override
	public List<String> save(List<TwitterIndexModel> documents) {
		List<IndexQuery> indexQueries = elasticIndexUtil
			.getIndexQueries(documents);
		List<String> ids = elasticsearchOperations.bulkIndex(
			indexQueries,
			IndexCoordinates.of(elasticConfigValues.getIndexName())
		).stream().map(IndexedObjectInformation::id).toList();
		log.info(
			"Indexed docs type={} ids={}",
			TwitterIndexModel.class.getName(),
			ids
		);
		return ids;
	}
}
