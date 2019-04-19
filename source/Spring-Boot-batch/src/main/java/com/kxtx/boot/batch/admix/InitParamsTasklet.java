package com.kxtx.boot.batch.admix;

import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.BatchOpType;
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
public class InitParamsTasklet implements Tasklet, BatchConstant {
    private static final Logger LOG = LoggerFactory.getLogger(InitParamsTasklet.class);
    private String bussnessIdKey;
    private BatchOpType type;

    public InitParamsTasklet(BatchOpType type, String bussnessIdKey) {
        this.type = type;
        this.bussnessIdKey = bussnessIdKey;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOG.info("InitParamsTasklet." + bussnessIdKey);
        ExecutionContext execContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        if (type == BatchOpType.In) {
            //TODO:根据requestId获取导入请求数据，并填充数据
            for (Map.Entry<String, Object> item : chunkContext.getStepContext().getJobParameters().entrySet()) {
                LOG.info(item.getKey() + ":" + item.getValue());
            }

        } else {
            //TODO:根据requestId获取导出请求数据，并填充数据
//            execContext.put(PARAM_OUTFILE, "file:target/outputFile" + new Random().nextInt() + ".csv");
//            execContext.put(PARAM_OUTCOLS, "id,name,email");
            execContext.put("PARAM_BEANNAME", "employeeService");
        }
        return RepeatStatus.FINISHED;
    }
}
