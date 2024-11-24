package com.microservices.demo.kafkatoelastic.consumer;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.TwitterIndexModel;
import com.microservices.demo.kafka.admin.KafkaAdminClient;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafkatoelastic.transformer.AvroToElasticTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {
	public static final String LISTENER_ID = "twitterTopicListener";

	@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
	// It is a false positive. IntelliJ's problem.
	private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private final KafkaAdminClient kafkaAdminClient;

	private final KafkaConfigValues kafkaConfigValues;

	private final AvroToElasticTransformer avroToElasticTransformer;

	private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

	@EventListener
	public void onAppStarted(ApplicationStartedEvent event) {
		kafkaAdminClient.checkTopicsCreated();
		log.info(
			"Topics with name {} is ready for operations!",
			kafkaConfigValues.getTopicNamesToCreate().toArray()
		);
		//noinspection DataFlowIssue
		kafkaListenerEndpointRegistry
			.getListenerContainer(LISTENER_ID).start();
	}

	@Override
	@KafkaListener(id = LISTENER_ID, topics = "${kafka-config.topic-name}")
	public void receive(
		@Payload List<TwitterAvroModel> messages,
		@Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
		@Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
		@Header(KafkaHeaders.OFFSET) List<Long> offsets
	) {
		log.info(
			"Thread={}. Received messages={} keys={} partitions={} offsets={}, " +
				"indexing to elastic",
			Thread.currentThread().getId(),
			messages.size(),
			keys.toString(),
			partitions.toString(),
			offsets.toString()
		);
		log.info("Converting to elastic models");
		List<TwitterIndexModel> elasticDocuments = avroToElasticTransformer
			.getElasticModels(messages);
		log.info("Sending to elastic");
		List<String> ids = elasticIndexClient.save(elasticDocuments);
		log.info("Saved documents to elasticsearch ids={}", ids.size());
	}
}
