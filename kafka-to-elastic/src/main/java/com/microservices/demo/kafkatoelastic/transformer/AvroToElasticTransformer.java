package com.microservices.demo.kafkatoelastic.transformer;

import com.microservices.demo.elastic.common.model.ElasticTwitterStatus;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.valueOf;
import static java.time.Instant.ofEpochMilli;
import static java.time.OffsetDateTime.ofInstant;
import static java.time.ZoneOffset.UTC;

@Component
public class AvroToElasticTransformer {
	public List<ElasticTwitterStatus> getElasticModels(List<TwitterAvroModel> avroModels) {
		return avroModels.stream()
			.map(avroModel -> ElasticTwitterStatus
				.builder()
				.userId(avroModel.getUserId())
				.id(valueOf(avroModel.getId()))
				.text(avroModel.getText())
				.createdAt(ofInstant(
					ofEpochMilli(avroModel.getCreatedAt()), UTC
				))
				.build()).toList();
	}

}
