package com.delight.gaia.base.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class TokenUtils {
    public static String generateToken(int size){
        return RandomStringUtils.randomAlphanumeric(size);
    }
    public static String generateUUIdToken(){
        return UUID.randomUUID().toString();
    }
}
