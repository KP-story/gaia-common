package com.delight.gaia.base.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Pair<T, V> {
    T left;
    V right;
}
