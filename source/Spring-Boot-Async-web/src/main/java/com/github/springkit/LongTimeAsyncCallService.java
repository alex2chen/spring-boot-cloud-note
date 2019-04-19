package com.github.springkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/8/30
 */
public class LongTimeAsyncCallService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LongTimeAsyncCallService.class);
    private int CorePoolSize = 4;
    private int delaySecond = 6;
    private Random random = new Random();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CorePoolSize);

    public LongTimeAsyncCallService(int delaySecond) {
        this.delaySecond = delaySecond;
    }

    public void exec(LongTermTaskCallback callback) {
        LOGGER.info("完成此任务需要等待:{}秒.", delaySecond);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("sync start.");
                callback.callback("callback result "+random.nextInt(10));
            }
        }, delaySecond, TimeUnit.SECONDS);
    }
}
