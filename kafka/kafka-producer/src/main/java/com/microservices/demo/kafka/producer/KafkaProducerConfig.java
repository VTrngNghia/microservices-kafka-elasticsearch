package com.microservices.demo.kafka.producer;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.appconfig.KafkaProducerConfigValues;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

	private final KafkaConfigValues kafkaConfigValues;

	private final KafkaProducerConfigValues kafkaProducerConfigValues;

	@Bean
	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
			kafkaConfigValues.getBootstrapServers()
		);
		props.put(
			kafkaConfigValues.getSchemaRegistryUrlKey(),
			kafkaConfigValues.getSchemaRegistryUrl()
		);
		props.put(
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
			kafkaProducerConfigValues.getKeySerializerClass()
		);
		props.put(
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
			kafkaProducerConfigValues.getValueSerializerClass()
		);
		props.put(
			ProducerConfig.BATCH_SIZE_CONFIG,
			kafkaProducerConfigValues.getBatchSize() *
				kafkaProducerConfigValues.getBatchSizeBoostFactor()
		);
		props.put(
			ProducerConfig.LINGER_MS_CONFIG,
			kafkaProducerConfigValues.getLingerMs()
		);
		props.put(
			ProducerConfig.COMPRESSION_TYPE_CONFIG,
			kafkaProducerConfigValues.getCompressionType()
		);
		props.put(
			ProducerConfig.ACKS_CONFIG,
			kafkaProducerConfigValues.getAcks()
		);
		props.put(
			ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
			kafkaProducerConfigValues.getRequestTimeoutMs()
		);
		props.put(
			ProducerConfig.RETRIES_CONFIG,
			kafkaProducerConfigValues.getRetryCount()
		);
		return props;
	}

	@Bean
	public ProducerFactory<K, V> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<K, V> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
