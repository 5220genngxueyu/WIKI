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
 import java.text.SimpleDateFormat;
 import java.util.Date;

 @Component
 public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Autowired
    private DocService service;
    @Resource
    private SnowFlake snowFlake;
    /**
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron() {
        //增加日志流水号
        MDC.put("LOG_ID",String.valueOf(snowFlake.nextId()));
        LOG.info("更新电子书下的文档数据开始");
        long start = System.currentTimeMillis();
        service.updateEbookInfo();
        LOG.info("更新结束，耗时"+(System.currentTimeMillis()-start));
    }

 }
