package com.microservices.demo.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class RetryConfig {
	private final RetryConfigValues retryConfigValues;

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
		exponentialBackOffPolicy.setInitialInterval(retryConfigValues.getInitialIntervalMs());
		exponentialBackOffPolicy.setMaxInterval(retryConfigValues.getMaxIntervalMs());
		exponentialBackOffPolicy.setMultiplier(retryConfigValues.getMultiplier());

		retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(retryConfigValues.getMaxAttempts());

		retryTemplate.setRetryPolicy(simpleRetryPolicy);

		return retryTemplate;
	}
}
