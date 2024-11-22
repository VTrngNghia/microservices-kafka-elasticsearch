package com.microservices.demo.kafka.admin;

import com.microservices.demo.appconfig.KafkaConfigValues;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Map;

@EnableRetry
@Configuration
@RequiredArgsConstructor
public class KafkaAdminConfig {
	private final KafkaConfigValues kafkaConfigValues;

	@Bean
	public AdminClient adminClient() {
		return AdminClient.create(
			Map.of(
				CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
				kafkaConfigValues.getBootstrapServers()
			)
		);
	}
}
