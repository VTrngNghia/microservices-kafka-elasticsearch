package com.microservices.demo.elastic.index.client;

import com.microservices.demo.appconfig.ElasticConfigValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static java.time.Duration.ofMillis;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic.index.client.repository")
public class ElasticConfig extends ElasticsearchConfiguration {
	private final ElasticConfigValues elasticConfigValues;

	@Override
	@NonNull
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
			.connectedTo(elasticConfigValues.getConnectionUrl())
			.withConnectTimeout(ofMillis(elasticConfigValues.getConnectTimeoutMs()))
			.withSocketTimeout(ofMillis(elasticConfigValues.getSocketTimeoutMs()))
			.build();
	}

}
