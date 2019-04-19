package com.kxtx.boot.config.partition;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.admix.AbstractJobTemplate;
import com.kxtx.boot.batch.listen.ShareStepExecutionListener;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/10.
 */
@Configuration
public class ImportPartitionJobConfiguration extends AbstractJobTemplate implements BatchConstant {
    private static final Logger logger = LoggerFactory.getLogger(ImportPartitionJobConfiguration.class);
    private static final String STEP_MASTER_NAME = "impMstPartitionStep";
    public static final String STEP_SLAVE_NAME = "impSlvPartitionStep";

    @Bean(IMPORT_PARTITION_NAME)
    public Job importCsvJob(@Qualifier(STEP_MASTER_NAME) Step step) {
        logger.info("init>>>importCsvJob2.");
        return createJob(IMPORT_PARTITION_NAME, step);
    }

    @Override
    public String[] getRequiredKeys() {
        String[] requiredKey = {PARAM_SEQ, PARAM_IN};
        return requiredKey;
    }

    @Bean(STEP_MASTER_NAME)
    @JobScope
    public Step importCsvStep(@Value(PARAM_IN_SCOPE) String inSetting, PartitionHandler partitionHandler) {
        logger.info("init>>>importCsvStep2.");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        //TODO:还需校验
        Step step = stepBuilders
                .get(STEP_MASTER_NAME).partitioner(STEP_SLAVE_NAME, new ImpRangePartitioner(request,inSetting)).partitionHandler(partitionHandler).listener(new ShareStepExecutionListener()).build();
        return step;
    }

    @Override
    public ItemReader<? extends BaseVO> createInItemReader(JobRequestContext request) {
        return null;
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BasePO> createInItemProcessor(JobRequestContext request) {
        return null;
    }

    @Override
    public ItemWriter<? super BasePO> createInItemWriter(JobRequestContext request) {
        return null;
    }

    @Override
    public ItemReader<? extends BaseVO> createOutItemReader(JobRequestContext request) {
        return null;
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BaseVO> createOutItemProcessor(JobRequestContext request) {
        return null;
    }

    @Override
    public ItemWriter<? super BaseVO> createOutItemWriter(JobRequestContext request) {
        return null;
    }

}
