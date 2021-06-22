package com.jiava.wiki.controller;

import com.jiava.wiki.req.DocQueryReq;
import com.jiava.wiki.req.DocSaveReq;
import com.jiava.wiki.resp.DocQueryResp;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp<List<DocQueryResp>> all(@Valid DocQueryReq req) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp<String> findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String string = docService.findContent(id);
        resp.setContent(string);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        docService.vote(id);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idStr}")
    public CommonResp delete(@PathVariable String idStr) {
        CommonResp resp = new CommonResp<>();

        List<String> ids = Arrays.asList(idStr.split(","));
        docService.delete(ids);
        return resp;
    }
}
