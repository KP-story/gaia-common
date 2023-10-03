package com.delight.gaia.jpa.repo;

import io.r2dbc.spi.Batch;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseRepo<Entity, ID> {

    <S extends Entity> Mono<S> save(S entity);

    <S extends Entity> Flux<S> saveAll(Iterable<S> entities);

    <S extends Entity> Flux<S> saveAll(Publisher<S> entityStream);

    Mono<Entity> findById(ID id);

    Mono<Entity> findById(Publisher<ID> id);

    Mono<Boolean> existsById(ID id);

    Mono<Boolean> existsById(Publisher<ID> id);

    Flux<Entity> findAll();

    Flux<Entity> findAllById(Iterable<ID> ids);

    Flux<Entity> findAllById(Publisher<ID> idStream);

    Mono<Long> count();

    Mono<Void> deleteById(ID id);

    Mono<Void> deleteById(Publisher<ID> id);

    Mono<Void> delete(Entity entity);

    Mono<Void> deleteAllById(Iterable<? extends ID> ids);

    Mono<Void> deleteAll(Iterable<? extends Entity> entities);

    Mono<Void> deleteAll(Publisher<? extends Entity> entityStream);

    Mono<Void> deleteAll();

    Mono<Void> saveInBatch(Iterable<? extends Entity> entities);

    Mono<Void> saveInBatch(Iterable<? extends Entity> entities, Batch batch);

}
