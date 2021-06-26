package com.jiava.wiki.service;

import com.jiava.wiki.domain.Test;
import com.jiava.wiki.mapper.EbookSnapshotMapperCust;
import com.jiava.wiki.mapper.TestMapper;
import com.jiava.wiki.resp.StatisticResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookSnapshotService {

    @Resource
    private  EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void dailyCount() {
        ebookSnapshotMapperCust.dailyCount();
    }

    public List<StatisticResp> getStatistic(){
        return ebookSnapshotMapperCust.getStatistic();
    }
    public List<StatisticResp> get30Statistic(){
        return ebookSnapshotMapperCust.get30Statistic();
    }
}
