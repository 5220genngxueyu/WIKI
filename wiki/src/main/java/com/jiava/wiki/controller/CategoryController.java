package com.jiava.wiki.controller;

import com.jiava.wiki.req.CategoryQueryReq;
import com.jiava.wiki.req.CategorySaveReq;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.resp.CategoryQueryResp;
import com.jiava.wiki.resp.PageResp;
import com.jiava.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp> > resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list=categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
