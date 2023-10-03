package com.delight.gaia.jpa.repo;

import io.r2dbc.spi.Batch;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AbsBaseRepo<Entity, ID, T extends JPABaseRepo<Entity, ID>> implements BaseRepo<Entity, ID> {
    @Autowired
    protected T jpaRepo;


    @Override
    public <S extends Entity> Mono<S> save(S entity) {
        return jpaRepo.save(entity);
    }

    @Override
    public <S extends Entity> Flux<S> saveAll(Iterable<S> entities) {
        return jpaRepo.saveAll(entities);
    }

    @Override
    public <S extends Entity> Flux<S> saveAll(Publisher<S> entityStream) {
        return jpaRepo.saveAll(entityStream);
    }

    @Override
    public Mono<Entity> findById(ID id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Mono<Entity> findById(Publisher<ID> id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return jpaRepo.existsById(id);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return jpaRepo.existsById(id);
    }

    @Override
    public Flux<Entity> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Flux<Entity> findAllById(Iterable<ID> ids) {
        return jpaRepo.findAllById(ids);
    }

    @Override
    public Flux<Entity> findAllById(Publisher<ID> idStream) {
        return jpaRepo.findAllById(idStream);
    }

    @Override
    public Mono<Long> count() {
        return jpaRepo.count();
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return jpaRepo.deleteById(id);
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        return jpaRepo.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Entity entity) {
        return jpaRepo.delete(entity);
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends ID> ids) {
        return jpaRepo.deleteAllById(ids);
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Entity> entities) {
        return jpaRepo.deleteAll(entities);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Entity> entityStream) {
        return jpaRepo.deleteAll(entityStream);
    }

    @Override
    public Mono<Void> deleteAll() {
        return jpaRepo.deleteAll();
    }

    @Override
    public Mono<Void> saveInBatch(Iterable<? extends Entity> entities) {
        return null;
    }

    @Override
    public Mono<Void> saveInBatch(Iterable<? extends Entity> entities, Batch batch) {
        return null;
    }


}
