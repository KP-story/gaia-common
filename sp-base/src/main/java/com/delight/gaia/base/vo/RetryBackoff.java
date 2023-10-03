package com.delight.gaia.base.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryBackoff {
    private long maxAttempts;
    private int minBackoffSeconds;
}
