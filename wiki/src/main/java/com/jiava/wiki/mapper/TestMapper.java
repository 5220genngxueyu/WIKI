package com.jiava.wiki.mapper;

import com.jiava.wiki.domain.Test;
import org.springframework.stereotype.Component;

import java.util.List;


public interface TestMapper {

    public List<Test> list();
}
