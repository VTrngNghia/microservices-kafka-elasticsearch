package com.microservices.demo.elastic.querier;

import com.microservices.demo.elastic.common.model.IndexModel;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ElasticQueryUtil<T extends IndexModel> {

	public Query getSearchQueryById(String id) {
		return NativeQuery.builder()
			.withIds(id)
			.build();
	}

	public Query getSearchQueryByFieldText(String field, String text) {
		return NativeQuery.builder()
			.withQuery(q -> q.bool(b -> b
				.must(m -> m.match(match -> match.field(field).query(text)))
			))
			.build();
	}

	public Query getSearchQueryForAll() {
		return NativeQuery.builder()
			.withQuery(q -> q.bool(b -> b
				.must(m -> m.matchAll(matchAll -> matchAll))
			))
			.build();
	}
}
