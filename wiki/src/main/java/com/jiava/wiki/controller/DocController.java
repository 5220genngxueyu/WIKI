package com.jiava.wiki.controller;

import com.jiava.wiki.req.DocQueryReq;
import com.jiava.wiki.req.DocSaveReq;
import com.jiava.wiki.resp.DocQueryResp;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp<List<DocQueryResp>> all(@Valid DocQueryReq req){
        CommonResp<List<DocQueryResp> > resp = new CommonResp<>();
        List<DocQueryResp> list=docService.all();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req){
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        docService.delete(id);
        return resp;
    }
}
