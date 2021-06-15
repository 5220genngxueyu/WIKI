package com.jiava.wiki.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiava.wiki.util.JsonLongSerializer;

public class EbookQueryReq extends PageReq{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long id;

    private String name;

}
