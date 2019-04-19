package com.kxtx.boot.config;

import com.alibaba.fastjson.JSON;
import com.kxtx.boot.batch.listen.ShareStepExecutionListener;
import com.kxtx.boot.batch.process.CommonOutProcessor;
import com.kxtx.boot.batch.reader.ItemReaderFactory;
import com.kxtx.boot.batch.writer.ItemWriterFactory;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.BatchOpType;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.admix.AbstractJobTemplate;
import com.kxtx.boot.batch.admix.ExportTypeDecider;
import com.kxtx.boot.batch.admix.InitParamsTasklet;
import com.kxtx.boot.batch.admix.TakeCountTasklet;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
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
 * @date 2017/9/21
 */
@Configuration
public class ExportCsvJobConfiguration extends AbstractJobTemplate implements BatchConstant {
    private static final Logger logger = LoggerFactory.getLogger(ImportCsvJobConfiguration.class);
    private static final String STEP_NAME_INIT = "initParamsStep";
    private static final String STEP_NAME_TAKECOUNT = "takeCountStep";
    private static final String STEP_NAME_EXPORTCSV = "exportCsvStep";
    private static final String STEP_NAME_EXPORTEXL = "exportExlStep";


    @Autowired
    private ResourceLoader resourceLoader;

    /*
        @Qualifier(STEP_NAME_INIT) Step init,
        @Qualifier(STEP_NAME_TAKECOUNT) Step takeCount,
        @Qualifier(STEP_NAME_EXPORTCSV) Step exportCsv,
        @Qualifier(STEP_NAME_EXPORTEXL) Step exportExl
     */
    @Bean(EXPORT_CSV_NAME)
    public Job exportCsvJob(@Qualifier(STEP_NAME_EXPORTCSV) Step exportCsv) {
        logger.info("init>>>exportCsvJob.");
        return createJob(EXPORT_CSV_NAME, exportCsv);
//        return jobBuilders
//                .get(EXPORT_CSV_NAME)
//                .incrementer(new RunIdIncrementer())
//                .repository(jobRepository)
//                .start(initParamsStep())
//                .next(takeCountStep())
//                .next(exportCsv)
//                .build();
    }

    @Override
    public String[] getRequiredKeys() {
        String[] requiredKey = {PARAM_SEQ, PARAM_OUT, PARAM_REQUESTVO};
        return requiredKey;
    }

    @Deprecated
    public JobExecutionDecider decider() {
        return new ExportTypeDecider(ItemWriterFactory.Csv);//TODO:需要封装
    }

    @Deprecated
    public Step initParamsStep() {
        logger.info("initParamsStep>>>.");
        return stepBuilders.get(STEP_NAME_INIT).tasklet(new InitParamsTasklet(BatchOpType.out, "PARAM_BUSSNESSID")).listener(new ShareStepExecutionListener()).build();
    }

    //@Bean(STEP_NAME_TAKECOUNT)
    //@JobScope
    @Deprecated
    public Step takeCountStep() {
        logger.info("takeCountStep>>>.");
        OutServiceAgent<BaseVO, BasePO> outServiceAgent = getOutServiceAgent("PARAM_BEANNAME");
        return stepBuilders.get(STEP_NAME_TAKECOUNT).tasklet(new TakeCountTasklet(outServiceAgent)).build();
    }

    @Bean(STEP_NAME_EXPORTCSV)
    @JobScope
    public Step exportCsvStep(@Value(PARAM_OUT_SCOPE) String outSetting, @Value(PARAM_REQUESTVO_SCOPE) String requestVO) {
        logger.info("init>>>exportCsvStep.");
        JobRequestContext request = new JobRequestContext();
        JobRequestContext.ExportSetting exportSetting = JSON.parseObject(outSetting, JobRequestContext.ExportSetting.class);
        request.setOut(exportSetting);
        request.setRequestVO(requestVO);
        //TODO:还需校验
        return createOutStep(STEP_NAME_EXPORTCSV, request);
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
        return (ItemWriter<? super BaseVO>) ItemWriterFactory.Csv.create(request, null, resourceLoader);
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
