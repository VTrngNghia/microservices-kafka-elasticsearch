package com.microservices.demo.elasticsearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic")
public class ElasticSearcher {
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearcher.class, args);
	}
}
