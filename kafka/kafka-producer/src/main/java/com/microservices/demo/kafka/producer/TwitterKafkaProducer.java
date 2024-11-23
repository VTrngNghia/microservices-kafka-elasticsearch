package com.microservices.demo.kafka.producer;

import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {
	private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

	@Override
	public void send(String topic, Long key, TwitterAvroModel value) {
		log.info("Sending TwitterAvroModel topic={} msg={}", topic, value);
		CompletableFuture<SendResult<Long, TwitterAvroModel>> future = kafkaTemplate
			.send(topic, key, value);
		future.whenComplete(handleFuture(topic, value));
	}

	private BiConsumer<SendResult<Long, TwitterAvroModel>, Throwable> handleFuture(
		String topic, TwitterAvroModel value
	) {
		return (result, ex) -> {
			if (ex == null) {
				RecordMetadata metadata = result.getRecordMetadata();
				log.info(
					"Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
					metadata.topic(),
					metadata.partition(),
					metadata.offset(),
					metadata.timestamp(),
					System.nanoTime()
				);
			} else {
				log.error(
					"Error while sending message {} to topic {}",
					value, topic, ex
				);
			}
		};
	}
}
