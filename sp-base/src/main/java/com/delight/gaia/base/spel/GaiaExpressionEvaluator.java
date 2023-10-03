package com.delight.gaia.base.spel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class GaiaExpressionEvaluator {

    private final SpelExpressionParser parser;

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * Create a new instance with the specified {@link SpelExpressionParser}.
     */
    protected GaiaExpressionEvaluator(SpelExpressionParser parser) {
        Assert.notNull(parser, "SpelExpressionParser must not be null");
        this.parser = parser;
    }

    /**
     * Create a new instance with a default {@link SpelExpressionParser}.
     */
    protected GaiaExpressionEvaluator() {
        this(new SpelExpressionParser());
    }


    /**
     * Return the {@link SpelExpressionParser} to use.
     */
    protected SpelExpressionParser getParser() {
        return this.parser;
    }

    /**
     * Return a shared parameter name discoverer which caches data internally.
     *
     * @since 4.3
     */
    protected ParameterNameDiscoverer getParameterNameDiscoverer() {
        return this.parameterNameDiscoverer;
    }


    /**
     * Return the {@link Expression} for the specified SpEL value
     * <p>Parse the expression if it hasn't been already.
     *
     * @param cache      the cache to use
     * @param elementKey the element on which the expression is defined
     * @param expression the expression to parse
     */
    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       AnnotatedElementKey elementKey, String expression) {

        ExpressionKey expressionKey = createKey(elementKey, expression);
        Expression expr = cache.get(expressionKey);
        if (expr == null) {
            expr = getParser().parseExpression(expression);
            cache.put(expressionKey, expr);
        }
        return expr;
    }

    public Argument getArgValue(String name, Object[] argValue, Method method) {
        List<String> parameterNames = Arrays.asList(getParameterNameDiscoverer().getParameterNames(method));
        int i = parameterNames.indexOf(name);
        if (i == -1) {
            throw new RuntimeException("not found field   " + name);
        }
        Argument argument = new Argument();
        argument.setIndex(i);
        argument.setValue(argValue[i]);
        return argument;
    }


    public EvaluationContext createEvaluationContext(
            Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod,
            @Nullable BeanFactory beanFactory) {

        ExpressionRootObject rootObject = new ExpressionRootObject(
                method, args, target, targetClass);
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(
                rootObject, targetMethod, args, getParameterNameDiscoverer());

        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    private ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
        return new ExpressionKey(elementKey, expression);
    }


    /**
     * An expression key.
     */
    protected static class ExpressionKey implements Comparable<ExpressionKey> {

        private final AnnotatedElementKey element;

        private final String expression;

        protected ExpressionKey(AnnotatedElementKey element, String expression) {
            Assert.notNull(element, "AnnotatedElementKey must not be null");
            Assert.notNull(expression, "Expression must not be null");
            this.element = element;
            this.expression = expression;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpressionKey)) {
                return false;
            }
            ExpressionKey otherKey = (ExpressionKey) other;
            return (this.element.equals(otherKey.element) &&
                    ObjectUtils.nullSafeEquals(this.expression, otherKey.expression));
        }

        @Override
        public int hashCode() {
            return this.element.hashCode() * 29 + this.expression.hashCode();
        }

        @Override
        public String toString() {
            return this.element + " with expression \"" + this.expression + "\"";
        }

        @Override
        public int compareTo(ExpressionKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0) {
                result = this.expression.compareTo(other.expression);
            }
            return result;
        }
    }

}
