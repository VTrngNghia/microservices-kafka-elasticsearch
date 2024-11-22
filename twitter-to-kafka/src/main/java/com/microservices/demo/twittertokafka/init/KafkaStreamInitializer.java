package com.microservices.demo.twittertokafka.init;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.kafka.admin.KafkaAdminClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaStreamInitializer implements StreamInitializer {
	private final KafkaConfigValues kafkaConfigValues;

	private final KafkaAdminClient kafkaAdminClient;

	@Override
	public void init() {
		kafkaAdminClient.createTopics();
		kafkaAdminClient.checkSchemaRegistry();
		log.info(
			"Topics with name {} is ready for operations!",
			kafkaConfigValues.getTopicNamesToCreate()
		);

	}
}
