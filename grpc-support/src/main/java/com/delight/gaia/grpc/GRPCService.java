package com.delight.gaia.grpc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(HttpClientRegistrar.class)
public @interface GRPCService {
}
