package com.delight.gaia.auth.annotation;

import com.delight.gaia.auth.context.SecurityUtils;
import com.delight.gaia.auth.subject.Subject;
import com.delight.gaia.base.constant.MessageCode;
import com.delight.gaia.base.exception.CommandFailureException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
@Component
public class AuthAspect {

    //
    @Around("@annotation(requiredLogin)")
    public Object requiredLogin(ProceedingJoinPoint joinPoint, RequiredLogin requiredLogin) {
        Signature signature = joinPoint.getSignature();
        Class returnType = ((MethodSignature) signature).getReturnType();
        Mono<Subject> subjectMono = SecurityUtils.getRequester().flatMap(subject -> {
            if (subject.getId() < 0) {
                return Mono.error(new CommandFailureException(MessageCode.REQUIRED_LOGIN));
            } else {
                return Mono.just(subject);
            }

        });
        if (returnType.isAssignableFrom(Mono.class)) {
            return subjectMono.flatMap(subject -> {
                try {
                    return (Mono<?>) joinPoint.proceed(joinPoint.getArgs());
                } catch (Throwable throwable) {
                    return Mono.error(throwable);
                }

            });
        } else if (returnType.isAssignableFrom(Flux.class)) {
            return subjectMono.flatMapMany(subject -> {
                try {
                    return (Flux<?>) joinPoint.proceed(joinPoint.getArgs());
                } catch (Throwable throwable) {
                    return Flux.error(throwable);
                }

            });
        } else {
            throw new RuntimeException("not support method return without mono or flux");
        }


    }


    @Around("@annotation(requiredPermission)")
    public Object requiredPermission(ProceedingJoinPoint joinPoint, RequiredPermission requiredPermission) {
        Signature signature = joinPoint.getSignature();
        Class returnType = ((MethodSignature) signature).getReturnType();
        Mono<Boolean> sub = SecurityUtils.getRequester().flatMap(subject -> subject.checkPermission(requiredPermission.value()));
        if (returnType.isAssignableFrom(Mono.class)) {
            return sub.flatMap(subject -> {
                try {
                    return (Mono<?>) joinPoint.proceed(joinPoint.getArgs());
                } catch (Throwable throwable) {
                    return Mono.error(throwable);
                }

            });
        } else if (returnType.isAssignableFrom(Flux.class)) {
            return sub.flatMapMany(bool -> {
                try {
                    return (Flux<?>) joinPoint.proceed(joinPoint.getArgs());
                } catch (Throwable throwable) {
                    return Flux.error(throwable);
                }
            });
        } else {
            throw new RuntimeException("not support method return without mono or flux");
        }


    }

//     try {
//        return (Flux<?>) joinPoint.proceed(joinPoint.getArgs());
//    } catch (Throwable throwable) {
//        return Flux.error(throwable);
//    }
}
