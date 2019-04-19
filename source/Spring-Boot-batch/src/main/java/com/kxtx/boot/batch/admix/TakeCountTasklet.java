package com.kxtx.boot.batch.admix;

import com.google.common.collect.Maps;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/23
 */
@Deprecated
public class TakeCountTasklet implements Tasklet, BatchConstant {
    private static final Logger LOG = LoggerFactory.getLogger(TakeCountTasklet.class);
    private OutServiceAgent<BaseVO, BasePO> outServiceAgent;

    public TakeCountTasklet(OutServiceAgent<BaseVO, BasePO> outServiceAgent) {
        this.outServiceAgent = outServiceAgent;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOG.info("TakeCountTasklet");
        ExecutionContext execContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
//        execContext.get()//TODO:需先封装，再获取请求参数
        Map map = Maps.newHashMap();
        map.put("id", 1);
        int result = outServiceAgent.getItemCount(map);
//        execContext.put(PARAM_ITEMCOUNT, result);
        return RepeatStatus.FINISHED;
    }
}
