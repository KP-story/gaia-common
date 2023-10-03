package com.delight.gaia.jpa.repo;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Result;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.StatementMapper;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.data.util.ProxyUtils;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.r2dbc.core.PreparedOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Consumer;

public class BatchRepo<T> {

    protected final DatabaseClient databaseClient;
    protected final StatementMapper statementMapper;
    private final MappingContext<? extends RelationalPersistentEntity<?>, ? extends RelationalPersistentProperty> mappingContext;


    public BatchRepo(R2dbcEntityTemplate r2dbcEntityTemplate) throws Exception {
        this.databaseClient = r2dbcEntityTemplate.getDatabaseClient();
        this.mappingContext = r2dbcEntityTemplate.getDataAccessStrategy().getConverter().getMappingContext();
        this.statementMapper = r2dbcEntityTemplate.getDataAccessStrategy().getStatementMapper();
    }

    public DatabaseClient getDatabaseClient() {
        return databaseClient;
    }

    public Class<T> defineEntity() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private <T> RelationalPersistentEntity<T> getRequiredEntity(T entity) {
        Class<?> entityType = ProxyUtils.getUserClass(entity);
        return (RelationalPersistentEntity) getRequiredEntity(entityType);
    }

    private RelationalPersistentEntity<?> getRequiredEntity(Class<?> entityClass) {
        return this.mappingContext.getRequiredPersistentEntity(entityClass);
    }

    public Mono<Batch> createBatch() {
        return Mono.from(databaseClient.getConnectionFactory().create()).map(connection -> connection.createBatch());
    }


    public Mono<Void> insertBatch(List<T> entities) {
        return Mono.empty();
    }

    public Mono<Void> insertBatch(List<T> entities, Batch batch) {
        return Mono.empty();
    }

    private <T> String doInsertSql(T entity, SqlIdentifier tableName, OutboundRow outboundRow) {

        StatementMapper.InsertSpec insert = statementMapper.createInsert(tableName);

        for (SqlIdentifier column : outboundRow.keySet()) {
            Parameter settableValue = outboundRow.get(column);
            if (settableValue.hasValue()) {
                insert = insert.withColumn(column, settableValue);
            }
        }

        PreparedOperation<?> operation = statementMapper.getMappedObject(insert);
        return operation.get();
    }
}
