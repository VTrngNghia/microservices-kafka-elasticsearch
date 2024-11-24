package com.microservices.demo.elastic.querier;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.querier.repository.TwitterElasticQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticQuerier implements ElasticQuerier<ElasticTwitterStatus> {
	private final TwitterElasticQueryRepository twitterElasticQueryRepository;

	@Override
	public ElasticTwitterStatus getById(String id) {

		return twitterElasticQueryRepository
			.findById(id)
			.map(t -> {
				log.info("Retrieved document id={}", t.getId());
				return t;
			})
			.orElseThrow(() ->
				new ElasticQuerierException("Can't find document id=" + id)
			);
	}

	@Override
	public List<ElasticTwitterStatus> getByText(String text) {
		List<ElasticTwitterStatus> searchResult = twitterElasticQueryRepository
			.findByText(text);
		log.info(
			"Retrieved documents count={} text={}", searchResult.size(), text
		);
		return searchResult;
	}

	@Override
	public List<ElasticTwitterStatus> getAll() {
		List<ElasticTwitterStatus> searchResult = Streamable.of(
			twitterElasticQueryRepository.findAll()
		).toList();
		log.info("Retrieved documents count={}", searchResult.size());
		return searchResult;
	}
}
