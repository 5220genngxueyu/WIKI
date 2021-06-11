package com.jiava.wiki.service;

import com.jiava.wiki.domain.Ebook;
import com.jiava.wiki.domain.EbookExample;
import com.jiava.wiki.mapper.EbookMapper;
import com.jiava.wiki.req.EbookReq;
import com.jiava.wiki.resp.EbookResp;
import com.jiava.wiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        List<EbookResp> respList = CopyUtil.copyList(ebookList,EbookResp.class);
        return respList;
    }
}
