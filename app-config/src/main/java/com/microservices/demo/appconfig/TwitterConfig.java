package com.microservices.demo.appconfig;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka")
@Getter
public class TwitterConfig {
	private List<String> twitterKeywords;

	private String welcomeMessage;

	private int mockMinTweetLength;

	private int mockMaxTweetLength;

	private int mockSleepMs;
}
