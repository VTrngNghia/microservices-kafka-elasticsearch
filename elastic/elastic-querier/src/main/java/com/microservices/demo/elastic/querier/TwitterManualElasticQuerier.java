package com.microservices.demo.elastic.querier;

import com.microservices.demo.appconfig.ElasticConfigValues;
import com.microservices.demo.appconfig.ElasticQueryConfigValues;
import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Mono.just;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterManualElasticQuerier implements ElasticQuerier<ElasticTwitterStatus> {

	private final ElasticConfigValues elasticConfigValues;

	private final ElasticQueryConfigValues elasticQueryConfigValues;

	private final ElasticsearchOperations elasticsearchOperations;

	private final ElasticQueryUtil<ElasticTwitterStatus> elasticQueryUtil;

	@Override
	public Mono<ElasticTwitterStatus> getById(String id) {
		Query query = elasticQueryUtil.getSearchQueryById(id);
		SearchHit<ElasticTwitterStatus> hit = elasticsearchOperations.searchOne(
			query,
			ElasticTwitterStatus.class,
			IndexCoordinates.of(elasticConfigValues.getIndexName())
		);
		if (hit == null) {
			throw new ElasticQuerierException("Can't find document id=" + id);
		}
		log.info("Retrieved document id={}", hit.getId());
		return just(hit.getContent());
	}

	@Override
	public Mono<List<ElasticTwitterStatus>> getByText(String text) {
		Query query = elasticQueryUtil.getSearchQueryByFieldText(
			elasticQueryConfigValues.getTextField(), text
		);
		return search(query, "Retrieved documents count={} text={}", text);
	}

	@Override
	public Mono<List<ElasticTwitterStatus>> getAll() {
		Query query = elasticQueryUtil.getSearchQueryForAll();
		return search(query, "Retrieved documents count={}");
	}

	private Mono<List<ElasticTwitterStatus>> search(
		Query query,
		String logMessage,
		Object... logParams
	) {
		SearchHits<ElasticTwitterStatus> hit = elasticsearchOperations.search(
			query,
			ElasticTwitterStatus.class,
			IndexCoordinates.of(elasticConfigValues.getIndexName())
		);
		log.info(logMessage, hit.getTotalHits(), logParams);
		return just(hit.get()
			.map(SearchHit::getContent)
			.toList());
	}
}
