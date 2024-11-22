package com.microservices.demo.twittertokafka;

import com.microservices.demo.twittertokafka.init.StreamInitializer;
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
	@Qualifier("mockTwitterStreamRunner")
	private final StreamRunner twitterStreamRunner;

	private final StreamInitializer streamInitializer;

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafka.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("App started");
		streamInitializer.init();
		twitterStreamRunner.start();
	}

}
