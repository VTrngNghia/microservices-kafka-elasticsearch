package com.microservices.demo.twittertokafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


@Component
public class TwitterKafkaStatusListener implements StatusListener {
	private static final Logger log = LoggerFactory.getLogger(
		TwitterKafkaStatusListener.class);

	@Override
	public void onStatus(Status status) {
		log.info("Twitter status with text: {}", status.getText());
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {

	}

	@Override
	public void onStallWarning(StallWarning warning) {

	}

	@Override
	public void onException(Exception ex) {
		log.error("Error on status listener", ex);
	}
}
