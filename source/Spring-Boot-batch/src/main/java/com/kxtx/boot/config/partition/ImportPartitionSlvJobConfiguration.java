package com.kxtx.boot.config.partition;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.listen.CustomChunkListener;
import com.kxtx.boot.batch.listen.CustomChunkListenerSupport;
import com.kxtx.boot.batch.listen.CustomItemWriterListener;
import com.kxtx.boot.batch.listen.CustomRetryListenerImpl;
import com.kxtx.boot.batch.process.CommonInProcessor;
import com.kxtx.boot.batch.reader.CsvFileItemReader;
import com.kxtx.boot.batch.reader.ItemReaderFactory;
import com.kxtx.boot.batch.writer.ItemWriterFactory;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.config.ImportCsvJobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/11.
 */
@Configuration
public class ImportPartitionSlvJobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ImportPartitionSlvJobConfiguration.class);
    @Autowired
    protected JobBuilderFactory jobBuilders;
    @Autowired
    protected StepBuilderFactory stepBuilders;
    @Autowired
    protected JobRepository jobRepository;
    @Autowired
    protected PlatformTransactionManager transactionManager;
    @Autowired
    protected ApplicationContext applicationContext;

    @Bean(ImportPartitionJobConfiguration.STEP_SLAVE_NAME)
//    @JobScope
    @StepScope   //不能使用，
    public Step importSlvCsvStep(@Qualifier("createInItemReader") FlatFileItemReader<? extends BaseVO> itemReader, @Qualifier("createInmProcessor") ItemProcessor<BaseVO, BasePO> itemProcessor, @Qualifier("createInItemWriter") ItemWriter<? super BasePO> itemWriter) {
        logger.info("init>>>importSlvCsvStep2.");
        //TODO:还需校验
        return createInStep(ImportPartitionJobConfiguration.STEP_SLAVE_NAME, itemReader, itemProcessor, itemWriter);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<? extends BaseVO> createInItemReader(@Value("#{stepExecutionContext['importSetting']}") String inSetting) {
        logger.info("init>>>createInItemReader2.");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        CsvFileItemReader<BaseVO> readerCsv = new CsvFileItemReader<BaseVO>();
        readerCsv.setLinesToSkip(1);
        ClassPathResource resource = new ClassPathResource(request.getIn().getInputFilePath());
        readerCsv.getRowCount(resource);
        //更新总行数
        readerCsv.setResource(resource);
        readerCsv.setLineMapper(readerCsv.createLineMapper(request.getIn().getInputCols(), request.getIn().getInputAlias(), request.getIn().getSourceType()));
        return readerCsv;
    }

    @Bean
    @StepScope
    public ItemProcessor<BaseVO, BasePO> createInmProcessor(@Value("#{stepExecutionContext['importSetting']}") String inSetting) {
        logger.info("init>>>createInmProcessor2.");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(request.getIn().getDelegateBean());
        Assert.notNull(inServiceAgent, "未找到对于的业务对象,请再检查配置项" + request.getIn().getDelegateBean());
        return new CommonInProcessor(request.getIn().getTargetType(), request.getIn().isCopyProperty(), inServiceAgent);
    }

    @Bean
    @StepScope
    public ItemWriter<? super BasePO> createInItemWriter(@Value("#{stepExecutionContext['importSetting']}") String inSetting) {
        logger.info("init>>>createInItemWriter2");
        JobRequestContext request = new JobRequestContext();
        request.setIn(JSON.parseObject(inSetting, JobRequestContext.ImportSetting.class));
        InServiceAgent<BaseVO, BasePO> inServiceAgent = getInServiceAgent(request.getIn().getDelegateBean());
        return (ItemWriter<? super BasePO>) ItemWriterFactory.Local.create(request, inServiceAgent, null);
    }

    protected InServiceAgent<BaseVO, BasePO> getInServiceAgent(String beanName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(beanName), "启动参数配置有误,beanName为必填项.");
        return (InServiceAgent<BaseVO, BasePO>) applicationContext.getBean(beanName);
    }

    protected Step createInStep(String stepName, FlatFileItemReader<? extends BaseVO> itemReader, ItemProcessor<BaseVO, BasePO> itemProcessor, ItemWriter<? super BasePO> itemWriter) {
        TaskletStep step = stepBuilders
                .get(stepName)
                .<BaseVO, BasePO>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .stream(itemReader)//fixed ItemReader open/closed
                .listener(new CustomChunkListenerSupport())
                .listener(new CustomRetryListenerImpl())
                .listener(new CustomItemWriterListener())
//                .transactionManager(transactionManager)
                .build();
        step.registerChunkListener(new CustomChunkListener());
        //step.getTasklet().execute();
        return step;
    }
}
