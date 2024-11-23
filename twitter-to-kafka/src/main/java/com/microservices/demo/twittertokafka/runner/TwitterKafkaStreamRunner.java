package com.microservices.demo.twittertokafka.runner;

import com.microservices.demo.appconfig.TwitterConfig;
import com.microservices.demo.twittertokafka.listener.TwitterKafkaStatusListener;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
//@ConditionalOnProperty(
//	value = "twitter-to-kafka.enable-mock-tweets",
//	havingValue = "false"
//)
@Profile("disabled")
@Slf4j
public class TwitterKafkaStreamRunner implements StreamRunner {

	private final TwitterConfig twitterConfig;

	private final TwitterKafkaStatusListener twitterKafkaStatusListener;

	private TwitterStream twitterStream;

	@Override
	public void start() {
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(twitterKafkaStatusListener);
		addFilter();
	}

	@PreDestroy
	public void destroy() {
		if (twitterStream != null) {
			log.info("Closing twitter stream");
			twitterStream.shutdown();
		}
	}

	private void addFilter() {
		String[] keywords = twitterConfig.getTwitterKeywords()
			.toArray(new String[0]);
		FilterQuery filterQuery = new FilterQuery(keywords);
		twitterStream.filter(filterQuery);
		log.info(
			"Started filtering twitter stream for keywords: {}",
			Arrays.toString(keywords)
		);
	}
}
