package com.kxtx.boot.batch.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/23
 */
public class CustomSkipListenerImpl<T, S> implements SkipListener<T, S> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomSkipListenerImpl.class);

    @Override
    public void onSkipInRead(Throwable throwable) {
        if (throwable instanceof FlatFileParseException){
            ((FlatFileParseException)throwable).getLineNumber();
        }
        LOG.error("onSkipInRead", throwable);
    }

    @Override
    public void onSkipInWrite(S s, Throwable throwable) {
        LOG.error("onSkipInWrite,data:" + s.toString(), throwable);
    }

    @Override
    public void onSkipInProcess(T t, Throwable throwable) {
        LOG.error("onSkipInWrite,data:" + t.toString(), throwable);
    }
}
