package com.delight.gaia.jpa.entity;

import com.delight.gaia.base.vo.VersionedDTO;
import org.springframework.data.annotation.Version;


public class VersionedEntity extends TraceableEntity implements VersionedDTO<Long> {
    @Version
    private Long updateVersion;

    @Override
    public Long getUpdateVersion() {
        return updateVersion;
    }

    @Override
    public void setUpdateVersion(Long updatedVersion) {
        this.updateVersion = updatedVersion;
    }
}
