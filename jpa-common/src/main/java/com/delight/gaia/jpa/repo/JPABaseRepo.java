package com.delight.gaia.jpa.repo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface JPABaseRepo<Bean, ID> extends ReactiveCrudRepository<Bean, ID> {

}
