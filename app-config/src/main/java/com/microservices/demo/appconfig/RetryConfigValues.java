package com.microservices.demo.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigValues {

	private Long initialIntervalMs;

	private Long maxIntervalMs;

	private Double multiplier;

	private Integer maxAttempts;

	private Long sleepTimeMs;
}
