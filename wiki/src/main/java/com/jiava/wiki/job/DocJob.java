 package com.jiava.wiki.job;

 import com.jiava.wiki.service.DocService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Component;

 import java.text.SimpleDateFormat;
 import java.util.Date;

 @Component
 public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Autowired
    private DocService service;
    /**
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron() {
        service.updateEbookInfo();
    }

 }
