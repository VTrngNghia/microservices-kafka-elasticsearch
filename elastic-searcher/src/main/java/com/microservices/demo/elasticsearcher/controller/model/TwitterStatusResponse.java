package com.microservices.demo.elasticsearcher.controller.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterStatusResponse extends RepresentationModel<TwitterStatusResponse> {
	private String id;

	private Long userId;

	private String text;

	private LocalDateTime createdAt;
}
