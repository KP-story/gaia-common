package com.delight.gaia.jpa.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CustomRepo {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    protected R2dbcEntityTemplate getTemplate() {
        return this.r2dbcEntityTemplate;
    }

    public <T> Flux<T> query(String query, List<Object> params, Class<T> resultClass) {
        DatabaseClient.GenericExecuteSpec sql = getTemplate().getDatabaseClient().sql(query);
        for (int i = 0; i < params.size(); i++) {
            sql = sql.bind(i, params.get(i));
        }
        return sql.map((row, rowMetadata) -> r2dbcEntityTemplate.getConverter().read(resultClass, row, rowMetadata)).all();
    }

    public <T> Mono<T> queryOne(String query, List<Object> params, Class<T> resultClass) {
        DatabaseClient.GenericExecuteSpec sql = getTemplate().getDatabaseClient().sql(query);
        for (int i = 0; i < params.size(); i++) {
            sql = sql.bind(i, params.get(i));
        }
        return sql.map((row, rowMetadata) -> r2dbcEntityTemplate.getConverter().read(resultClass, row, rowMetadata)).one();
    }
}
