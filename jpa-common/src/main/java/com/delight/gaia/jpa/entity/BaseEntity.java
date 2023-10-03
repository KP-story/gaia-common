package com.delight.gaia.jpa.entity;

import com.delight.gaia.base.vo.IdentifiableDTO;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;


@EqualsAndHashCode()
public class BaseEntity implements IdentifiableDTO<Long> {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
