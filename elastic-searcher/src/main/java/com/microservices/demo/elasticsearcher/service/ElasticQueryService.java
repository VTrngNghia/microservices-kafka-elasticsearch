package com.microservices.demo.elasticsearcher.service;

import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;

import java.util.List;

public interface ElasticQueryService {

	TwitterStatusResponse getDocumentById(String id);

	List<TwitterStatusResponse> getDocumentByText(String text);

	List<TwitterStatusResponse> getAllDocuments();
}

