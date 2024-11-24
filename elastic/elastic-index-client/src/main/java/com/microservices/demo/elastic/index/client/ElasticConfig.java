package com.microservices.demo.elastic.index.client;

import com.microservices.demo.appconfig.ElasticConfigValues;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static java.time.Duration.ofMillis;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic.index.client.repository")
public class ElasticConfig extends ElasticsearchConfiguration {
	private final ElasticConfigValues elasticConfigValues;

	@Override
	@NonNull
	public ClientConfiguration clientConfiguration() {
		log.info(
			"Creating Elasticsearch client configuration with connection url={}",
			elasticConfigValues.getConnectionUrl()
		);
		return ClientConfiguration.builder()
			.connectedTo(elasticConfigValues.getConnectionUrl())
			.withConnectTimeout(ofMillis(elasticConfigValues.getConnectTimeoutMs()))
			.withSocketTimeout(ofMillis(elasticConfigValues.getSocketTimeoutMs()))
			.build();
	}

	@PostConstruct
	public void postConstruct() {
		log.info(
			"Elasticsearch client url={}",
			elasticConfigValues.getConnectionUrl()
		);
	}
}
