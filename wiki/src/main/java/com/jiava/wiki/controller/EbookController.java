package com.jiava.wiki.controller;

import com.jiava.wiki.domain.Demo;
import com.jiava.wiki.domain.Ebook;
import com.jiava.wiki.req.EbookReq;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.resp.EbookResp;
import com.jiava.wiki.service.DemoService;
import com.jiava.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp> > resp = new CommonResp<>();
        List<EbookResp> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
