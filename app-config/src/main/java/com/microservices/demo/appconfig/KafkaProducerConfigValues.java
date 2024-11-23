package com.microservices.demo.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigValues {
	private String keySerializerClass;

	private String valueSerializerClass;

	private String compressionType;

	private String acks;

	private Integer batchSize;

	private Integer batchSizeBoostFactor;

	private Integer lingerMs;

	private Integer requestTimeoutMs;

	private Integer retryCount;
}
