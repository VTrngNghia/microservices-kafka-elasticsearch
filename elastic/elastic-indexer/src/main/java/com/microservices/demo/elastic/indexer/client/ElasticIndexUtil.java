package com.microservices.demo.elastic.indexer.client;

import com.microservices.demo.elastic.common.model.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticIndexUtil<T extends IndexModel> {
	public List<IndexQuery> getIndexQueries(List<T> documents) {
		return documents.stream()
			.map(document -> new IndexQueryBuilder()
				.withId(document.getId())
				.withObject(document)
				.build()
			).toList();
	}
}