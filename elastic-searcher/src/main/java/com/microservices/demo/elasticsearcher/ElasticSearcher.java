package com.microservices.demo.elasticsearcher;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic")
@OpenAPIDefinition(info = @Info(title = "Elastic Searcher", version = "1.0"))
public class ElasticSearcher {
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearcher.class, args);
	}
}
