package com.kxtx.boot.batch.writer;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.client.BaseVO;

import com.kxtx.boot.exception.BreakException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import javax.batch.operations.BatchRuntimeException;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public class LocalBatchItemWriter<T extends BasePO> implements CustomItemWriter {
    private static final Logger logger = LoggerFactory.getLogger(LocalBatchItemWriter.class);
    private boolean assertUpdates = true;
    private InServiceAgent<? extends BaseVO, ? extends BasePO> inServiceAgent;

    public void setAssertUpdates(boolean assertUpdates) {
        this.assertUpdates = assertUpdates;
    }

    @Override
    public void setServiceAgent(InServiceAgent inServiceAgent) {
        this.inServiceAgent = inServiceAgent;
    }

    @Override
    public void write(List list) throws Exception {
        logger.info("write start:" + JSON.toJSONString(list, true));
        if (!list.isEmpty()) {
            if (list.size() == list.stream().filter(x -> ((BasePO) x).isRetry()).count()) {
                return;
            }
            //list = (List) list.stream().limit(1).collect(Collectors.toList());
            int results = inServiceAgent.batchWriter(list);
            if (this.assertUpdates) {
                if (results == 0) {
                    //TODO:重试总是发生1次，关闭不了
                    for (Object item : list) {
                        ((BasePO) item).setRetry(true);
                    }
                    logger.info("write end error:" + JSON.toJSONString(list, true));
//                    throw new RuntimeException("Batch execution returned invalid results");
                    throw new BreakException("提前终止吧");
                }
            }
        }
    }
}
