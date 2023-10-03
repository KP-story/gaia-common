package com.delight.gaia.base.vo;

import java.time.LocalDateTime;
import java.util.Date;

public interface TraceableDTO<ID> extends IdentifiableDTO<ID> {

    Byte getStatus();

    void setStatus(Byte status);

    LocalDateTime getCreatedTime();

    void setCreatedTime(LocalDateTime createdTime);

    LocalDateTime getUpdatedTime();

    void setUpdatedTime(LocalDateTime updatedTime);
}
