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
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long category2Id;

    public Long getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }

    private String name;

    @Override
    public String toString() {
        return "EbookQueryReq{" +
                "id=" + id +
                ", category2Id=" + category2Id +
                ", name='" + name + '\'' +
                '}';
    }
}
