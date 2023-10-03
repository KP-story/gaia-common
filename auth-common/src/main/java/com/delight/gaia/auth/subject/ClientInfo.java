package com.delight.gaia.auth.subject;

import com.delight.gaia.base.constant.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ClientInfo {
    protected String deviceId;
    protected String  ip;
    protected Platform platform;
    protected String app;
}
