package com.microservices.demo.twittertokafka;

import com.microservices.demo.twittertokafka.init.StreamInitializer;
import com.microservices.demo.twittertokafka.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import twitter4j.TwitterException;

@RequiredArgsConstructor
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafka {
	private final StreamRunner twitterStreamRunner;

	private final StreamInitializer streamInitializer;

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafka.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void init() throws TwitterException {
		log.info("App ready event");
		streamInitializer.init();
		twitterStreamRunner.start();
	}
}
