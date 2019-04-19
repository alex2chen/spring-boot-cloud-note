package com.kxtx.boot.batch.process;

import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.InServiceAgent;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public class CommonInProcessor implements ItemProcessor<BaseVO, BasePO> {
    private Class<? extends BasePO> targetType;
    private boolean isCopyProperties;
    private InServiceAgent<BaseVO, BasePO> serviceAgent;

    public CommonInProcessor(Class<? extends BasePO> targetType, boolean isCopyProperties, InServiceAgent<BaseVO, BasePO> serviceAgent) {
        this.targetType = targetType;
        this.isCopyProperties = isCopyProperties;
        this.serviceAgent = serviceAgent;
    }

    @Override
    public BasePO process(BaseVO baseVO) throws Exception {
        BasePO basePO = targetType.newInstance();
        if (isCopyProperties) {
            BeanUtils.copyProperties(baseVO, basePO);
        }
        serviceAgent.translatePODataItem(baseVO, basePO);
        return basePO;
    }
}
