package com.microservices.demo.kafkatoelastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@ComponentScan(basePackages = "com.microservices.demo")
@SpringBootApplication
public class KafkaToElastic {

	public static void main(String[] args) {
		SpringApplication.run(KafkaToElastic.class, args);
	}

}
