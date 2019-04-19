package com.kxtx.boot.batch.process;

import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public class CommonOutProcessor implements ItemProcessor<BaseVO, BaseVO> {
    private static final Logger logger = LoggerFactory.getLogger(CommonOutProcessor.class);
    private OutServiceAgent<BaseVO, BasePO> serviceAgent;

    public CommonOutProcessor(OutServiceAgent<BaseVO, BasePO> serviceAgent) {
        this.serviceAgent = serviceAgent;
    }

    @Override
    public BaseVO process(BaseVO baseVO) throws Exception {
        logger.info("process>>>");
        serviceAgent.partTranslateDataItem(baseVO);
        return baseVO;
    }
}
