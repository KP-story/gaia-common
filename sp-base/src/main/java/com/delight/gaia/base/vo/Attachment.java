package com.delight.gaia.base.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
@Getter
@Setter
@Accessors(chain = true)
public class Attachment {
    private String url;
    private String contentType;
    private Payload payload;
    @Getter
    @Setter
    @Accessors(chain = true)
    public  static class Payload{
        private Long id;
        private Map<String, String> attributes;
        private String name;
    }
}
