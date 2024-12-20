package com.microservices.demo.twittertokafka.transformer;

import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {
	public TwitterAvroModel toAvro(Status status) {
		return TwitterAvroModel
			.newBuilder()
			.setId(status.getId())
			.setUserId(status.getUser().getId())
			.setText(status.getText())
			.setCreatedAt(status.getCreatedAt().getTime())
			.build();
	}

}
