package com.microservices.demo.elastic.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

@Data
@Builder
@Document(indexName = "#{@elasticConfigValues.getIndexName()}")
public class ElasticTwitterStatus implements IndexModel {
	@JsonProperty
	private String id;

	@JsonProperty
	private Long userId;

	@JsonProperty
	private String text;

	@Field(type = Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
	@JsonFormat(shape = STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
	@JsonProperty
	private OffsetDateTime createdAt;
}
