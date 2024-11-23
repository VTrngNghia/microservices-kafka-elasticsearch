package com.microservices.demo.twittertokafka.runner;

import com.microservices.demo.appconfig.TwitterConfig;
import com.microservices.demo.twittertokafka.exception.TwitterToKafkaServiceException;
import com.microservices.demo.twittertokafka.listener.TwitterKafkaStatusListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
//@ConditionalOnProperty(
//	value = "twitter-to-kafka.enable-mock-tweets",
//	havingValue = "true"
//)
@Slf4j
public class MockTwitterStreamRunner implements StreamRunner {

	private static final Random RANDOM = new Random();

	private static final String[] WORDS = new String[]{
		"Lorem",
		"ipsum",
		"dolor",
		"sit",
		"amet",
		"consectetuer",
		"adipiscing",
		"elit",
		"Maecenas",
		"porttitor",
		"congue",
		"massa",
		"Fusce",
		"posuere",
		"magna",
		"sed",
		"pulvinar",
		"ultricies",
		"purus",
		"lectus",
		"malesuada",
		"libero"
	};

	private static final String tweetAsRawJson = "{" +
		"\"created_at\":\"{0}\"," +
		"\"id\":\"{1}\"," +
		"\"text\":\"{2}\"," +
		"\"user\":{\"id\":\"{3}\"}" +
		"}";

	private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

	private final TwitterConfig twitterToKafkaServiceConfig;

	private final TwitterKafkaStatusListener twitterKafkaStatusListener;


	@Override
	public void start() throws TwitterException {
		String[] keywords = twitterToKafkaServiceConfig.getTwitterKeywords()
			.toArray(new String[0]);
		int minTweetLength = twitterToKafkaServiceConfig.getMockMinTweetLength();
		int maxTweetLength = twitterToKafkaServiceConfig.getMockMaxTweetLength();
		long sleepTimeMs = twitterToKafkaServiceConfig.getMockSleepMs();
		log.info(
			"Starting mock filtering twitter streams for keywords {}",
			Arrays.toString(keywords)
		);
		//noinspection InfiniteLoopStatement
		while (true) {
			String formattedTweetAsRawJson = getFormattedTweet(
				keywords,
				minTweetLength,
				maxTweetLength
			);
			Status status = TwitterObjectFactory.createStatus(
				formattedTweetAsRawJson);
			twitterKafkaStatusListener.onStatus(status);
			sleep(sleepTimeMs);
		}
	}

	private void sleep(long sleepTimeMs) {
		try {
			Thread.sleep(sleepTimeMs);
		} catch (InterruptedException e) {
			throw new TwitterToKafkaServiceException(
				"Error while sleeping for waiting new status to create!!");
		}
	}

	private String getFormattedTweet(
		String[] keywords,
		int minTweetLength,
		int maxTweetLength
	) {
		String[] params = new String[]{
			ZonedDateTime.now().format(DateTimeFormatter.ofPattern(
				TWITTER_STATUS_DATE_FORMAT)),
			String.valueOf(ThreadLocalRandom.current()
				.nextLong(Long.MAX_VALUE)),
			getRandomTweetContent(keywords, minTweetLength, maxTweetLength),
			String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
		};
		String tweet = tweetAsRawJson;

		for (int i = 0; i < params.length; i++) {
			tweet = tweet.replace("{" + i + "}", params[i]);
		}
		return tweet;
	}

	private String getRandomTweetContent(
		String[] keywords,
		int minTweetLength,
		int maxTweetLength
	) {
		StringBuilder tweet = new StringBuilder();
		int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;
		for (int i = 0; i < tweetLength; i++) {
			tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
			if (i == tweetLength / 2) {
				tweet.append(keywords[RANDOM.nextInt(keywords.length)])
					.append(" ");
			}
		}
		return tweet.toString().trim();
	}

}

