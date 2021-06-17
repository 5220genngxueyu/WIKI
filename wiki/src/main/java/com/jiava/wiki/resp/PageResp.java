package com.jiava.wiki.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiava.wiki.util.JsonLongSerializer;

import java.util.List;

public class PageResp<T> {

    private Long total;

    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
