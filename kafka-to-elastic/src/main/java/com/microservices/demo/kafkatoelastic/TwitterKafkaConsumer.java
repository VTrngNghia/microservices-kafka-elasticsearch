package com.microservices.demo.kafkatoelastic;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.kafka.admin.KafkaAdminClient;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
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
	@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
	// It is a false positive. IntelliJ's problem.
	private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private final KafkaAdminClient kafkaAdminClient;

	private final KafkaConfigValues kafkaConfigValues;

	@EventListener
	public void onAppStarted(ApplicationStartedEvent event) {
		kafkaAdminClient.checkTopicsCreated();
		log.info(
			"Topics with name {} is ready for operations!",
			kafkaConfigValues.getTopicNamesToCreate().toArray()
		);
		kafkaListenerEndpointRegistry.getListenerContainer(
			"twitterTopicListener").start();
	}


	@Override
	@KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
	public void receive(
		@Payload List<TwitterAvroModel> messages,
		@Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
		@Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
		@Header(KafkaHeaders.OFFSET) List<Long> offsets
	) {
		log.info(
			"{} number of message received with keys {}, partitions {} and offsets {}, " +
				"sending it to elastic: Thread id {}",
			messages.size(),
			keys.toString(),
			partitions.toString(),
			offsets.toString(),
			Thread.currentThread().getId()
		);
	}
}
