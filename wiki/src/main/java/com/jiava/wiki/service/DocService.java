package com.jiava.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiava.wiki.config.WikiApplication;
import com.jiava.wiki.domain.Doc;
import com.jiava.wiki.domain.DocExample;
import com.jiava.wiki.mapper.DocMapper;
import com.jiava.wiki.req.DocQueryReq;
import com.jiava.wiki.req.DocSaveReq;
import com.jiava.wiki.resp.DocQueryResp;
import com.jiava.wiki.resp.PageResp;
import com.jiava.wiki.util.CopyUtil;
import com.jiava.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    @Resource
    private DocMapper docMapper;
    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq req) {

        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        docExample.setOrderByClause("sort asc");
        //PageHelper只会对之后第一条查询数据生效,所以和要分页的sql放在一起
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo=new PageInfo<>(docList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp=new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public List<DocQueryResp> all(DocQueryReq req) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
       if(!ObjectUtils.isEmpty(req.getEbookId())){
           criteria.andEbookIdEqualTo(req.getEbookId());
       }
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }
//    保存
    public void save(DocSaveReq req){
        Doc doc=CopyUtil.copy(req,Doc.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        }
        else{
            //更新
            docMapper.updateByPrimaryKey(doc);
        }
    }
    //删除
    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }
}
