package com.kxtx.boot.batch.listen;

import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.client.BasePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

/**
 * Created by YT on 2017/10/24.
 * TODO:异常处理,可替换ItemWriteListenerAdapter
 */
public class CustomItemWriterListener implements ItemWriteListener<BasePO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemWriterListener.class);
    private JobRequestContext request;

    public CustomItemWriterListener() {

    }

    public CustomItemWriterListener(JobRequestContext request) {
        this.request = request;
    }

    @Override
    public void beforeWrite(List<? extends BasePO> target) {
        //TODO:过滤不了，估计是框架的bug
//        target = target.stream().limit(1).collect(Collectors.toList());
//        LOGGER.info("beforeWrite:"+request.getIn().getInputFilePath()+","+target.size());
    }

    @Override
    public void afterWrite(List<? extends BasePO> list) {
        if (request == null) return;
        LOGGER.info("afterWrite:" + request.getIn().getInputFilePath() + "," + list.size());
    }

    @Override
    public void onWriteError(Exception e, List<? extends BasePO> list) {
        LOGGER.info("onWriteError:" + e);
    }
}
