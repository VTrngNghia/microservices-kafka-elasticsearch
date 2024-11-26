package com.microservices.demo.elasticsearcher.controller.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterStatusResponseV2 extends RepresentationModel<TwitterStatusResponseV2> {
	private Long id;

	private Long userId;

	private String text;

	private String createdAt;
}
