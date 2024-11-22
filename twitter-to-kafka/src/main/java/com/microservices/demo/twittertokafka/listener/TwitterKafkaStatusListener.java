package com.microservices.demo.twittertokafka.listener;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.KafkaProducer;
import com.microservices.demo.twittertokafka.transformer.TwitterStatusToAvroTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;


@Component
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {
	private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

	private final KafkaConfigValues kafkaConfigValues;

	private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;

	@Override
	public void onStatus(Status status) {
		log.info(
			"Twitter status text={} kafka topic={}",
			status.getText(),
			kafkaConfigValues.getTopicName()
		);
		TwitterAvroModel avro = twitterStatusToAvroTransformer.toAvro(status);
		kafkaProducer.send(
			kafkaConfigValues.getTopicName(), status.getId(), avro
		);
	}
}
