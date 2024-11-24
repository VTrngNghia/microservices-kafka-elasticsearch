package com.microservices.demo.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "elastic-config")
public class ElasticConfigValues {
	private String indexName;

	private String connectionUrl;

	private Integer connectTimeoutMs;

	private Integer socketTimeoutMs;
}

