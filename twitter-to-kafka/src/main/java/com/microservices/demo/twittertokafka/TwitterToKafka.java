package com.microservices.demo.twittertokafka;

import com.microservices.demo.appconfig.TwitterToKafkaConfig;
import com.microservices.demo.twittertokafka.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafka implements CommandLineRunner {
	private final TwitterToKafkaConfig twitterToKafkaConfig;

	@Qualifier("mockTwitterStreamRunner")
	private final StreamRunner twitterStreamRunner;

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafka.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("App started");
		log.info(twitterToKafkaConfig.getWelcomeMessage());
		log.info(twitterToKafkaConfig.getTwitterKeywords().toString());
		twitterStreamRunner.start();
	}
}
