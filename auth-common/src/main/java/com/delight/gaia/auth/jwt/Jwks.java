package com.delight.gaia.auth.jwt;

import com.delight.gaia.auth.api.dto.response.Jwk;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Jwks {
    private List<Jwk> keys;
}
