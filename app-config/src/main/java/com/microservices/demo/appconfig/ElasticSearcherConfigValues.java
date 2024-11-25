package com.microservices.demo.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-searcher")
public class ElasticSearcherConfigValues {
	private String version;
}
