/**
 *
 */
package com.kxtx.boot.config;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.reader.ItemReaderFactory;
import com.kxtx.boot.batch.writer.ItemWriterFactory;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.admix.AbstractJobTemplate;
import com.kxtx.boot.batch.process.CommonInProcessor;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.config.partition.ImportPartitionJobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

/**
 */
@Configuration
public class ImportCsvJobConfiguration extends AbstractJobTemplate implements BatchConstant {
    private static final Logger logger = LoggerFactory.getLogger(ImportCsvJobConfiguration.class);
    private static final String STEP_NAME = "importCsvStep";
    //@Autowired
    //private ResourceLoader resourceLoader;

    @Bean(IMPORT_CSV_NAME)
    public Job importCsvJob(@Qualifier(STEP_NAME) Step step) {
        logger.info("init>>>importCsvJob.");
        return createJob(IMPORT_CSV_NAME, step);
    }

    @Override
    public String[] getRequiredKeys() {
        String[] requiredKey = {PARAM_SEQ, PARAM_IN};
        return requiredKey;
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step importCsvStep(@Value(PARAM_IN_SCOPE) String inSetting) {
        logger.info("init>>>importCsvStep.");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        //TODO:还需校验
        return createInStep(STEP_NAME, request);
    }

    @Override
    public ItemReader<? extends BaseVO> createInItemReader(JobRequestContext request) {
        logger.info("init>>>createbItemReader");
        return ItemReaderFactory.Csv.create(request, null);
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BasePO> createInItemProcessor(JobRequestContext request) {
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(request.getIn().getDelegateBean());
        Assert.notNull(inServiceAgent, "未找到对于的业务对象,请再检查配置项" + request.getIn().getDelegateBean());
        return new CommonInProcessor(request.getIn().getTargetType(), request.getIn().isCopyProperty(), inServiceAgent);
    }

    @Override
    public ItemWriter<? super BasePO> createInItemWriter(JobRequestContext request) {
        logger.info("init>>>createInItemWriter");
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(request.getIn().getDelegateBean());
        return (ItemWriter<? super BasePO>) ItemWriterFactory.Local.create(request, inServiceAgent, null);
    }

    @Override
    public ItemReader<? extends BaseVO> createOutItemReader(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BaseVO> createOutItemProcessor(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }

    @Override
    public ItemWriter<? super BaseVO> createOutItemWriter(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }
}
