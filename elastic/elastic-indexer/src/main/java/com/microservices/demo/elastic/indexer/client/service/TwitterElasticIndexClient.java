package com.microservices.demo.elastic.indexer.client.service;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.indexer.client.repository.TwitterElasticIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticIndexClient<T extends ElasticTwitterStatus> implements ElasticIndexClient<T> {
	private final TwitterElasticIndexRepository twitterElasticIndexRepository;

	@Override
	public List<String> save(List<T> documents) {
		Iterable<T> repositoryResponse = twitterElasticIndexRepository
			.saveAll(documents);
		List<String> ids = StreamSupport
			.stream(repositoryResponse.spliterator(), false)
			.map(ElasticTwitterStatus::getId)
			.toList();
		log.info(
			"Indexed docs type={} ids={}",
			ElasticTwitterStatus.class.getName(),
			ids.size()
		);
		return ids;
	}
}
