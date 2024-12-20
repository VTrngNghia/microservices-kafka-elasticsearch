package com.microservices.demo.kafka.producer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
	void send(String topic, K key, V value);
}
