package com.microservices.demo.elasticsearcher.service;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elastic.querier.ElasticQuerier;
import com.microservices.demo.elasticsearcher.controller.assembler.TwitterResponseAssembler;
import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import com.microservices.demo.elasticsearcher.transformer.ElasticToResponseTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {
	private final ElasticQuerier<ElasticTwitterStatus> elasticQuerier;

	private final ElasticToResponseTransformer elasticToResponseTransformer;

	private final TwitterResponseAssembler twitterResponseAssembler;

	@Override
	public TwitterStatusResponse getDocumentById(String id) {
//		return elasticToResponseTransformer.toStatusResponse(
//			elasticQuerier.getById(id)
//		);
		return twitterResponseAssembler.toModel(
			elasticQuerier.getById(id)
		);
	}

	@Override
	public List<TwitterStatusResponse> getDocumentByText(String text) {
//		return elasticToResponseTransformer.toStatusesResponse(
//			elasticQuerier.getByText(text)
//		);
		return twitterResponseAssembler.toModels(
			elasticQuerier.getByText(text)
		);
	}

	@Override
	public List<TwitterStatusResponse> getAllDocuments() {
//		return elasticToResponseTransformer.toStatusesResponse(
//			elasticQuerier.getAll()
//		);
		return twitterResponseAssembler.toModels(elasticQuerier.getAll());
	}
}
