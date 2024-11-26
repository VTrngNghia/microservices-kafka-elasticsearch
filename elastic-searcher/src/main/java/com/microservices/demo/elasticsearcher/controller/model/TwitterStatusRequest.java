package com.microservices.demo.elasticsearcher.controller.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterStatusRequest {
	private String id;

	@NotEmpty
	private String text;
}
