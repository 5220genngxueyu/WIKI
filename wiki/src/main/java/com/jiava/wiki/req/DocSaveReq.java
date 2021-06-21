package com.jiava.wiki.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiava.wiki.util.JsonLongSerializer;

import javax.validation.constraints.NotNull;

public class DocSaveReq extends PageReq{
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class )
    @NotNull(message="【父文档】不能为空")
    private Long parent;
    @NotNull(message="【电子书】不能为空")
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long ebookId;
    @NotNull(message="【名称】不能为空")
    private String name;
    @NotNull(message="【顺序】不能为空")
    private Integer sort;
    @NotNull(message="【内容】不能为空")
    private String content;

    private Integer viewCount;

    private Integer voteCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DocSaveReq{" +
                "id=" + id +
                ", parent=" + parent +
                ", ebookId=" + ebookId +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", content='" + content + '\'' +
                ", viewCount=" + viewCount +
                ", voteCount=" + voteCount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
