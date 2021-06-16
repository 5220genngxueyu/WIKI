package com.jiava.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiava.wiki.config.WikiApplication;
import com.jiava.wiki.domain.Category;
import com.jiava.wiki.domain.CategoryExample;
import com.jiava.wiki.mapper.CategoryMapper;
import com.jiava.wiki.req.CategoryQueryReq;
import com.jiava.wiki.req.CategorySaveReq;
import com.jiava.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        categoryExample.setOrderByClause("sort asc");
        //PageHelper只会对之后第一条查询数据生效,所以和要分页的sql放在一起
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp=new PageResp<>();
        pageResp.setList(respList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return respList;
    }
//    保存
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }
        else{
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }
    //删除
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
