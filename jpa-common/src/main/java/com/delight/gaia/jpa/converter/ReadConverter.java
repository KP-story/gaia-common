package com.delight.gaia.jpa.converter;

import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;

public interface ReadConverter<T> extends Converter<Row, T> {
}
