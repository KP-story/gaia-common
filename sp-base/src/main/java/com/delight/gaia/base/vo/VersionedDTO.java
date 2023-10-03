package com.delight.gaia.base.vo;

public interface VersionedDTO<ID> extends TraceableDTO<ID> {

    Long getUpdateVersion();

    void setUpdateVersion(Long updatedVersion);

}
