package com.kxtx.boot.batch.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.context.RetryContextSupport;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/16
 */
public class CustomRetryListenerImpl implements RetryListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomRetryListenerImpl.class);

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {

        logger.warn("retry.open." + ((RetryContextSupport) context).toString());

        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
                                               Throwable throwable) {
        logger.warn("retry.close." + ((RetryContextSupport) context).toString());
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
                                                 Throwable throwable) {
        logger.error("retry.error:" + ((RetryContextSupport) context).toString() + throwable.getMessage());
        try {
            //callback.doWithRetry(context);
           logger.info("retry.error:"+(callback.getClass().getName()));
        } catch (Throwable e) {
            logger.error("retry.error:"+e.getMessage());
        }
        //((RetryContextSupport)context).registerThrowable(null);
    }
}