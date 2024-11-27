package com.microservices.demo.elastic.querier;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.querier.repository.TwitterElasticQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Mono.error;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticQuerier implements ElasticQuerier<ElasticTwitterStatus> {
	private final TwitterElasticQueryRepository twitterElasticQueryRepository;

	@Override
	public Mono<ElasticTwitterStatus> getById(String id) {

		return twitterElasticQueryRepository
			.findById(id)
			.doOnSuccess(t -> log.info("Retrieved document id={}", t.getId()))
			.switchIfEmpty(error(
				new ElasticQuerierException("Can't find document id=" + id)
			));
	}

	@Override
	public Mono<List<ElasticTwitterStatus>> getByText(String text) {
		return twitterElasticQueryRepository
			.findByText(text)
			.collectList()
			.doOnSuccess(res -> log.info(
				"Retrieved documents count={} text={}",
				res.size(),
				text
			))
			;
	}

	@Override
	public Mono<List<ElasticTwitterStatus>> getAll() {
		return twitterElasticQueryRepository
			.findAll()
			.collectList()
			.doOnSuccess(res -> log.info(
				"Retrieved documents count={}",
				res.size()
			));
	}
}
