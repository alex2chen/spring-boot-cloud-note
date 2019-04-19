package com.kxtx.boot.config;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.reader.ItemReaderFactory;
import com.kxtx.boot.batch.writer.ItemWriterFactory;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.admix.AbstractJobTemplate;
import com.kxtx.boot.batch.process.CommonOutProcessor;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
@Configuration
public class ExportXlsJobConfiguration extends AbstractJobTemplate implements BatchConstant {
    private static final Logger logger = LoggerFactory.getLogger(ExportXlsJobConfiguration.class);
    private static final String STEP_NAME = "exportXlsStep";
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean(EXPORT_XLS_NAME)
    public Job exportCsvJob(@Qualifier(STEP_NAME) Step exportCsv) {
        logger.info("init>>>exportCsvJob.");
        return createJob(EXPORT_CSV_NAME, exportCsv);
    }

    @Override
    public String[] getRequiredKeys() {
        String[] requiredKey = {PARAM_SEQ, PARAM_OUT, PARAM_REQUESTVO};
        return requiredKey;
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step exportCsvStep(@Value(PARAM_OUT_SCOPE) String outSetting, @Value(PARAM_REQUESTVO_SCOPE) String requestVO) {
        logger.info("init>>>exportCsvStep.");
        JobRequestContext request = new JobRequestContext();
        JobRequestContext.ExportSetting exportSetting = JSON.parseObject(outSetting, JobRequestContext.ExportSetting.class);
        request.setOut(exportSetting);
        request.setRequestVO(requestVO);
        //TODO:还需校验
        return createOutStep(STEP_NAME, request);
    }

    @Override
    public ItemReader<? extends BaseVO> createOutItemReader(JobRequestContext request) {
        OutServiceAgent<BaseVO, BasePO> outServiceAgent = getOutServiceAgent(request.getOut().getDelegateBean());
        Assert.notNull(outServiceAgent, "未找到对于的业务对象,请再检查配置项" + request.getOut().getDelegateBean());
        return ItemReaderFactory.Local.create(request, outServiceAgent);
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BaseVO> createOutItemProcessor(JobRequestContext request) {
        OutServiceAgent<BaseVO, BasePO> outServiceAgent = getOutServiceAgent(request.getOut().getDelegateBean());
        return new CommonOutProcessor(outServiceAgent);
    }

    @Override
    public ItemWriter<? super BaseVO> createOutItemWriter(JobRequestContext request) {
        logger.info("init>>>createOutItemWriter." + request.getOut().getOutFilePath());
        return (ItemWriter<? super BaseVO>) ItemWriterFactory.Xls.create(request, null, resourceLoader);
    }

    @Override
    public ItemReader<? extends BaseVO> createInItemReader(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }

    @Override
    public ItemProcessor<? super BaseVO, ? extends BasePO> createInItemProcessor(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }

    @Override
    public ItemWriter<? super BasePO> createInItemWriter(JobRequestContext request) {
        throw new RuntimeException("非法执行路径,请检查初始化信息.");
    }
}
