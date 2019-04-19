package com.github.springkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YT on 2018/5/22.
 */
@Service
public class TimerServiceImpl {
    private final static Logger LOGGER = LoggerFactory.getLogger(TimerServiceImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws InterruptedException {
        Thread.sleep(6000);
        LOGGER.info("每隔5秒执行一次 " + dateFormat.format(new Date()));
    }
}
