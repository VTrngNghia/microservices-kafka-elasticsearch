package com.microservices.demo.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-config")
public class ElasticConfigValues {
	private String indexName;

	private String connectionUrl;

	private Integer connectTimeoutMs;

	private Integer socketTimeoutMs;
}

