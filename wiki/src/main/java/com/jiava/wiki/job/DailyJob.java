 package com.jiava.wiki.job;

 import com.jiava.wiki.service.DocService;
 import com.jiava.wiki.util.SnowFlake;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.slf4j.MDC;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Component;

 import javax.annotation.Resource;

 @Component
 public class DailyJob {

    private static final Logger LOG = LoggerFactory.getLogger(DailyJob.class);

    @Autowired
    private DocService service;
    @Resource
    private SnowFlake snowFlake;
    /**
     * 每小时更新电子书信息
     */
    @Scheduled(cron = "59 59 0/1 * * ? ")
    public void cron() {
        //增加日志流水号
        MDC.put("LOG_ID",String.valueOf(snowFlake.nextId()));
        LOG.info("更新每日统计开始");
        long start = System.currentTimeMillis();
        service.dailyCount();
        LOG.info("更新结束，耗时"+(System.currentTimeMillis()-start));
    }

 }
