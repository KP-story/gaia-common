package com.delight.gaia.base.spel;

import org.springframework.aop.support.AopUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodArgsExpressionEvaluator extends GaiaExpressionEvaluator {
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    @Nullable
    public <T> T key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext, Class<T> t) {
        return getExpression(this.keyCache, methodKey, keyExpression).getValue(evalContext, t);
    }

    public EvaluationContext createEvaluationContext(Object object, Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        return super.createEvaluationContext(method, args, object, targetClass, targetMethod, null);
    }

    public <T> T parser(Object object, Object[] args, Class clazz, Method method, String expression, Class<T> returnClass) {
        EvaluationContext evaluationContext = this.createEvaluationContext(object, clazz, method, args);
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, clazz);
        return this.key(expression, methodKey, evaluationContext, returnClass);
    }


    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}

