package com.microservices.demo.kafkaconsumer;

import com.microservices.demo.appconfig.KafkaConfigValues;
import com.microservices.demo.appconfig.KafkaConsumerConfigValues;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {
	private final KafkaConfigValues kafkaConfigValues;

	private final KafkaConsumerConfigValues kafkaConsumerConfigValues;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
			kafkaConfigValues.getBootstrapServers()
		);
		props.put(
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
			kafkaConsumerConfigValues.getKeyDeserializer()
		);
		props.put(
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
			kafkaConsumerConfigValues.getValueDeserializer()
		);
		props.put(
			ConsumerConfig.GROUP_ID_CONFIG,
			kafkaConsumerConfigValues.getConsumerGroupId()
		);
		props.put(
			ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
			kafkaConsumerConfigValues.getAutoOffsetReset()
		);
		props.put(
			kafkaConfigValues.getSchemaRegistryUrlKey(),
			kafkaConfigValues.getSchemaRegistryUrl()
		);
		props.put(
			kafkaConsumerConfigValues.getSpecificAvroReaderKey(),
			kafkaConsumerConfigValues.getSpecificAvroReader()
		);
		props.put(
			ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
			kafkaConsumerConfigValues.getSessionTimeoutMs()
		);
		props.put(
			ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,
			kafkaConsumerConfigValues.getHeartbeatIntervalMs()
		);
		props.put(
			ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
			kafkaConsumerConfigValues.getMaxPollIntervalMs()
		);
		props.put(
			ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
			kafkaConsumerConfigValues.getMaxPartitionFetchBytesDefault() *
				kafkaConsumerConfigValues.getMaxPartitionFetchBytesBoostFactor()
		);
		props.put(
			ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
			kafkaConsumerConfigValues.getMaxPollRecords()
		);
		return props;
	}

	@Bean
	public ConsumerFactory<K, V> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(kafkaConsumerConfigValues.getBatchListener());
		factory.setConcurrency(kafkaConsumerConfigValues.getConcurrencyLevel());
		factory.setAutoStartup(kafkaConsumerConfigValues.getAutoStartup());
		factory.getContainerProperties()
			.setPollTimeout(kafkaConsumerConfigValues.getPollTimeoutMs());
		return factory;
	}
}
