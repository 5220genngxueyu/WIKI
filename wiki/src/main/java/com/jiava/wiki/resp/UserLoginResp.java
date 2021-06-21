package com.jiava.wiki.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiava.wiki.util.JsonLongSerializer;

public class UserLoginResp {
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long id;

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }

    @JsonSerialize(using = JsonLongSerializer.class )
    private Long token;

    private String loginName;

    private String name;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserLoginResp{" +
                "id=" + id +
                ", token=" + token +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}