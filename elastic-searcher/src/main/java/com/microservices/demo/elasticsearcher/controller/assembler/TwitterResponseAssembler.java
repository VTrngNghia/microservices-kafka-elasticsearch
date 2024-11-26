package com.microservices.demo.elasticsearcher.controller.assembler;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elasticsearcher.controller.ElasticDocumentController;
import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import com.microservices.demo.elasticsearcher.transformer.ElasticToResponseTransformer;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TwitterResponseAssembler
	extends RepresentationModelAssemblerSupport<ElasticTwitterStatus,
	TwitterStatusResponse>
{
	private final ElasticToResponseTransformer elasticToResponseTransformer;

	public TwitterResponseAssembler(
		ElasticToResponseTransformer elasticToResponseTransformer
	) {
		super(ElasticDocumentController.class, TwitterStatusResponse.class);
		this.elasticToResponseTransformer = elasticToResponseTransformer;
	}

	@Override
	public TwitterStatusResponse toModel(ElasticTwitterStatus status) {
		TwitterStatusResponse response = elasticToResponseTransformer.toStatusResponse(
			status
		).add(linkTo(ElasticDocumentController.class)
			.slash(status.getId())
			.withSelfRel()
		).add(linkTo(ElasticDocumentController.class)
			.withRel("documents")
		);
		return response;
	}

	public List<TwitterStatusResponse> toModels(List<ElasticTwitterStatus> statuses) {
		return statuses.stream()
			.map(this::toModel)
			.toList();
	}
}