package com.delight.gaia.base.mapper;

import org.mapstruct.MappingTarget;

import java.util.Collection;

public interface BaseMapper<A, B> {
    A bToA(B b);

    B aToB(A a);

    Collection<A> bsToAs(Collection<B> bs);

    Collection<B> asToBs(Collection<A> as);

    A bToA(B b, @MappingTarget A a);

    B aToB(A a, @MappingTarget B b);
}
