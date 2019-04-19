package com.github.springkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/8/30
 */
@RestController
public class DemoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
    private LongTimeAsyncCallService longTimeAsyncCallService = new LongTimeAsyncCallService(5);

    @GetMapping("/sync")
    public DeferredResult<String> sync() {
        DeferredResult<String> result = new DeferredResult<String>();
        LOGGER.info("start....");
        longTimeAsyncCallService.exec((ret) -> {
            LOGGER.info("sync complete...." + ret);
            result.setResult("OK");
        });
        return result;
    }

    @GetMapping("/sync-timeout")
    public DeferredResult<String> syncTimeout() {
        DeferredResult<String> deferredResult = new DeferredResult<String>(2000L);
        LOGGER.info("start....timeout:2s");
        longTimeAsyncCallService.exec(new LongTermTaskCallback() {
            @Override
            public void callback(Object result) {
                LOGGER.info("exec sync complete...." + result);
                deferredResult.setResult("Ok");
            }
        });

        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("exec timeout complete....");
                deferredResult.setResult("timeout");
            }
        });
        return deferredResult;
    }

    @GetMapping("/sync2")
    public WebAsyncTask<String> syncTask() {
        LOGGER.info("start....");
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(5000); //假设是一些长时间任务
                LOGGER.info("exec sync complete....");
                return "suc";
            }
        };
        return new WebAsyncTask(callable);
    }

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private static ThreadLocal<String> threadLocalInherit = new InheritableThreadLocal<String>();

    @GetMapping("/sync-timeout2")
    public WebAsyncTask<String> syncTaskTimeout() {
        threadLocal.set(Thread.currentThread().getName());
        threadLocalInherit.set(Thread.currentThread().getName());
        LOGGER.info("start....timeout:2s");
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                LOGGER.info("exec sync call...." + threadLocal.get() + "," + threadLocalInherit.get());
                Thread.sleep(5000); //假设是一些长时间任务
                LOGGER.info("exec sync complete...." + threadLocal.get() + "," + threadLocalInherit.get());
                return "suc";
            }
        };
        WebAsyncTask asyncTask = new WebAsyncTask(callable);
        asyncTask.onCompletion(() -> System.out.println("asyncTask.onCompletion..." + threadLocal.get() + "," + threadLocalInherit.get()));
        asyncTask.onTimeout(new Callable<String>() {
            public String call() throws Exception {
                LOGGER.info("exec timeout complete....");
                return "timeout";
            }
        });
        return asyncTask;
    }

}
