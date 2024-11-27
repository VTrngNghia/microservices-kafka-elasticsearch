package com.microservices.demo.elasticsearcher.service;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.querier.ElasticQuerier;
import com.microservices.demo.elasticsearcher.controller.assembler.TwitterResponseAssembler;
import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import com.microservices.demo.elasticsearcher.transformer.ElasticToResponseTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {
	private final ElasticQuerier<ElasticTwitterStatus> elasticQuerier;

	private final ElasticToResponseTransformer elasticToResponseTransformer;

	private final TwitterResponseAssembler twitterResponseAssembler;

	@Override
	public Mono<TwitterStatusResponse> getDocumentById(String id) {
		return elasticQuerier.getById(id)
			.map(twitterResponseAssembler::toModel)
			;
	}

	@Override
	public Mono<List<TwitterStatusResponse>> getDocumentByText(String text) {
		return elasticQuerier.getByText(text)
			.map(twitterResponseAssembler::toModels)
			;
	}

	@Override
	public Mono<List<TwitterStatusResponse>> getAllDocuments() {
		return elasticQuerier.getAll()
			.map(twitterResponseAssembler::toModels)
			;
	}
}
