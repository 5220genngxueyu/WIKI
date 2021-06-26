package com.jiava.wiki.controller;

import com.jiava.wiki.domain.Demo;
import com.jiava.wiki.resp.CommonResp;
import com.jiava.wiki.resp.StatisticResp;
import com.jiava.wiki.service.DemoService;
import com.jiava.wiki.service.EbookSnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {
    @Resource
    private EbookSnapshotService service;

    @GetMapping("/get-statistic")
   public CommonResp getStatistic(){
        List<StatisticResp> statisticResp = service.getStatistic();
        CommonResp< List<StatisticResp>> resp =new CommonResp<>();
        resp.setContent(statisticResp);
        return resp;
    }
    @GetMapping("/get-30-statistic")
   public CommonResp get30Statistic(){
        List<StatisticResp> statisticResp = service.get30Statistic();
        CommonResp< List<StatisticResp>> resp =new CommonResp<>();
        resp.setContent(statisticResp);
        return resp;
    }
}
