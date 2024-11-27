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
		return twitterElasticQueryService.getAllDocuments()
			.doOnSuccess(response -> log.info(
				"Returning all documents count={}",
				response.size()
			))
			.map(ResponseEntity::ok)
			.block()
			;
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
		return twitterElasticQueryService.getDocumentById(id)
			.doOnSuccess(response -> log.info("Returning document id={}", id))
			.map(ResponseEntity::ok)
			.block()
			;
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
		return twitterElasticQueryService
			.getDocumentByText(twitterStatusRequest.getText())
			.doOnSuccess(response -> log.info(
				"Returning documents by text count={}",
				response.size()
			))
			.map(ResponseEntity::ok)
			.block()
			;
	}
}
