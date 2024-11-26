package com.microservices.demo.elasticsearcher.transformer;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticToResponseTransformer {
	public TwitterStatusResponse toStatusResponse(ElasticTwitterStatus elasticTwitterStatus) {
		return TwitterStatusResponse
			.builder()
			.id(elasticTwitterStatus.getId())
			.userId(elasticTwitterStatus.getUserId())
			.text(elasticTwitterStatus.getText())
			.createdAt(elasticTwitterStatus.getCreatedAt().toLocalDateTime())
			.build();
	}

	public List<TwitterStatusResponse> toStatusesResponse(List<ElasticTwitterStatus> elasticTwitterStatuses) {
		return elasticTwitterStatuses.stream()
			.map(this::toStatusResponse)
			.toList();
	}
}
