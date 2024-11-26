package com.microservices.demo.elasticsearcher.controller;

import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusRequest;
import com.microservices.demo.elasticsearcher.controller.model.TwitterStatusResponse;
import com.microservices.demo.elasticsearcher.service.TwitterElasticQueryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/documents")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {
	private final TwitterElasticQueryService twitterElasticQueryService;


	@GetMapping
	@ApiResponse(responseCode = "200", description = "Get all documents",
		content = @Content(
			schema = @Schema(implementation = TwitterStatusResponse.class)
		)
	)
	public ResponseEntity<List<TwitterStatusResponse>> getDocuments() {
		List<TwitterStatusResponse> responses = twitterElasticQueryService
			.getAllDocuments();
		log.info(
			"Returning all documents count={}",
			responses.size()
		);
		return ok(responses);
	}

	@GetMapping("/{id}")
	@ApiResponse(description = "Get document by id",
		content = @Content(
			schema = @Schema(implementation = TwitterStatusResponse.class)
		)
	)
	public ResponseEntity<TwitterStatusResponse> getDocument(
		@PathVariable String id
	) {
		TwitterStatusResponse response = twitterElasticQueryService
			.getDocumentById(id);
		log.info("Returning document id={}", id);
		return ok(response);
	}

	@PostMapping("/search")
	@ApiResponse(description = "Search documents by text",
		content = @Content(
			schema = @Schema(implementation = TwitterStatusResponse.class)
		)
	)
	public ResponseEntity<List<TwitterStatusResponse>> searchDocuments(
		@RequestBody @Valid TwitterStatusRequest twitterStatusRequest
	) {
		List<TwitterStatusResponse> responses = twitterElasticQueryService
			.getDocumentByText(twitterStatusRequest.getText());
		log.info(
			"Returning documents by text count={}",
			responses.size()
		);
		return ok(responses);
	}
}
