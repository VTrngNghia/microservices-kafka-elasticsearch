package com.microservices.demo.elasticsearcher.service;

import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ElasticQueryService {

	Mono<TwitterStatusResponse> getDocumentById(String id);

	Mono<List<TwitterStatusResponse>> getDocumentByText(String text);

	Mono<List<TwitterStatusResponse>> getAllDocuments();
}

