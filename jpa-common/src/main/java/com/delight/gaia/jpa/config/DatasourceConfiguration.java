package com.delight.gaia.jpa.config;

import com.delight.gaia.jpa.converter.JsonReadConverter;
import com.delight.gaia.jpa.converter.JsonWriteConverter;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;

import java.util.ArrayList;
import java.util.List;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.INITIAL_SIZE;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

public abstract class DatasourceConfiguration {
    private ConfigurableEnvironment environment;
    private JpaConfig jpaConfig;
    @Autowired
    private List<JsonReadConverter> jsonReadConverters;
    @Autowired
    private List<JsonWriteConverter> jsonWriteConverters;
    private R2dbcDialect dialect;

    public DatasourceConfiguration(ConfigurableEnvironment environment) throws Exception {
        this.environment = environment;
        jpaConfig = Binder.get(environment)
                .bind("spring." + getPrefixConfig(), Bindable.of(JpaConfig.class))
                .orElse(null);
        Class dialect = Class.forName(jpaConfig.getDialect());
        this.dialect = (R2dbcDialect) dialect.getField("INSTANCE").get(null);
    }

    protected abstract String getPrefixConfig();


    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder().from(ConnectionFactoryOptions.parse(jpaConfig.getUrl()))
                        .option(USER, jpaConfig.getUser())
                        .option(PASSWORD, jpaConfig.getPassword())
                        .option(MAX_SIZE, jpaConfig.getMaximumPoolSize())
                        .option(INITIAL_SIZE, jpaConfig.getMinimumIdle())
                        .build());
    }

    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(dialect, getCustomConverters());
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .bindMarkers(dialect.getBindMarkersFactory())
                .build();

        return new R2dbcEntityTemplate(databaseClient, strategy);
    }

    protected List<Object> getCustomConverters() {
        List converters = new ArrayList(2);
        if (jsonReadConverters != null) {
            converters.addAll(jsonReadConverters);
        }
        if (jsonWriteConverters != null) {
            converters.addAll(jsonWriteConverters);
        }
        return converters;
    }


}
