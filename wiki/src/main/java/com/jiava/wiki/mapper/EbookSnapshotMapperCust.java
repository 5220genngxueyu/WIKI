package com.jiava.wiki.mapper;

import com.jiava.wiki.resp.StatisticResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface EbookSnapshotMapperCust {

    public void dailyCount();
    public List<StatisticResp> getStatistic();
    public List<StatisticResp> get30Statistic();

}
