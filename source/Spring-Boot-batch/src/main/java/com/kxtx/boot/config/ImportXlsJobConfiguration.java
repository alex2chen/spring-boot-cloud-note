package com.kxtx.boot.config;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.reader.ItemReaderFactory;
import com.kxtx.boot.batch.writer.ItemWriterFactory;
import com.kxtx.boot.batch.admix.AbstractJobTemplate;
import com.kxtx.boot.batch.process.CommonInProcessor;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.batch.JobRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
@Configuration
public class ImportXlsJobConfiguration extends AbstractJobTemplate implements BatchConstant {

    private static final Logger logger = LoggerFactory.getLogger(ImportXlsJobConfiguration.class);
    private static final String STEP_NAME = "importXlsStep";

    @Bean(IMPORT_XLS_NAME)
    public Job importCsvJob(@Qualifier(STEP_NAME) Step step) {
        logger.info("init>>>importXlsJob.");
        return createJob(IMPORT_XLS_NAME, step);
    }

    @Override
    public String[] getRequiredKeys() {
        String[] requiredKey = {PARAM_SEQ, PARAM_IN};
        return requiredKey;
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step importCsvStep(@Value(PARAM_IN_SCOPE) String inSetting) {
        logger.info("init>>>importXlsStep.");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        //TODO:还需校验
        return createInStep(STEP_NAME, request);
    }

    @Override
    public ItemReader<? extends BaseVO> createInItemReader(JobRequestContext request) {
        logger.info("init>>>createInItemReader");
        return ItemReaderFactory.Xls.create(request, null);
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BasePO> createInItemProcessor(JobRequestContext param) {
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(param.getIn().getDelegateBean());
        Assert.notNull(inServiceAgent, "未找到对于的业务对象,请再检查配置项" + param.getIn().getDelegateBean());
        return new CommonInProcessor(param.getIn().getTargetType(), param.getIn().isCopyProperty(), inServiceAgent);
    }

    @Override
    public ItemWriter<? super BasePO> createInItemWriter(JobRequestContext request) {
        logger.info("init>>>createInItemWriter");
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(request.getIn().getDelegateBean());
        return (ItemWriter<? super BasePO>) ItemWriterFactory.Local.create(request, inServiceAgent, null);
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
