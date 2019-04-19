package com.kxtx.boot.batch.admix;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.kxtx.boot.batch.listen.*;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.exception.BreakException;
import com.kxtx.boot.exception.CustomException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.FaultTolerantStepBuilder;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.TaskExecutorRepeatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.batch.operations.BatchRuntimeException;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/20
 */
public abstract class AbstractJobTemplate {
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

    protected Job createJob(String jobName, Step step) {
        return jobBuilders
                .get(jobName)
//                .incrementer(new RunIdIncrementer())
                .validator(defaultParamValidator())
                .repository(jobRepository)
//                .flow(step)
//                .end()
                .start(step)
                .build();
    }

    protected Step createInStep(String stepName, JobRequestContext request) {
//        SimpleStepBuilder<BaseVO, BasePO> stepBuilder = stepBuilders.get(stepName)
//                .<BaseVO, BasePO>chunk(request.getChunkSize())
//                .reader(createInItemReader(request))
//                .processor(createInItemProcessor(request))
//                .writer(createInItemWriter(request));
//        FaultTolerantStepBuilder<BaseVO, BasePO> baseVOBasePOFaultTolerantStepBuilder = stepBuilder.faultTolerant();
        //baseVOBasePOFaultTolerantStepBuilder.noRollback()
        TaskletStep step = stepBuilders
                .get(stepName)
                .<BaseVO, BasePO>chunk(request.getChunkSize())
                .reader(createInItemReader(request))
                .processor(createInItemProcessor(request))
                .writer(createInItemWriter(request))
//                .chunkOperations(new TaskExecutorRepeatTemplate())
                .faultTolerant()
                .listener(new CustomChunkListenerSupport())
//                .noRollback(BatchRuntimeException.class)
//                .retry(RuntimeException.class)   //重试,总是发生一次，关不了感觉是它的bug，TODO:可以采用BitMap记录每行执行情况
//                .retry(BatchRuntimeException.class)//BatchRuntimeException
                .noRetry(RuntimeException.class)
//                .retryLimit(0)           //每条记录重试一次
                .retryPolicy(new NeverRetryPolicy())
                .listener(new CustomRetryListenerImpl())
//                .noSkip(BreakException.class)
                .skip(Exception.class)
                .skipLimit(500)         //一共允许跳过200次异常
                .listener(new CustomItemWriterListener(request))
//                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置并发方式执行
//                .throttleLimit(10)     //并发任务数为10,默认为4
                .transactionManager(transactionManager)
                .build();
        step.registerChunkListener(new CustomChunkListener());
        return step;
    }

    protected Step createOutStep(String stepName, JobRequestContext request) {
        return stepBuilders
                .get(stepName)
                .<BaseVO, BaseVO>chunk(request.getChunkSize())
                .reader(createOutItemReader(request))
                .processor(createOutItemProcessor(request))
                .writer(createOutItemWriter(request))
                .faultTolerant()
                .retry(Exception.class)   //重试
                .noRetry(ParseException.class)
                .retryLimit(1)           //每条记录重试一次
                .listener(new CustomRetryListenerImpl())
                .skip(Exception.class)        //会导致查询出错也跳出
                .skipLimit(500)         //一共允许跳过200次异常
                .listener(new CustomSkipListenerImpl<BaseVO, BaseVO>())
//                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置并发方式执行
//                .throttleLimit(10)     //并发任务数为10,默认为4
                .transactionManager(transactionManager)
                .build();
    }

    private JobParametersValidator defaultParamValidator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
        validator.setRequiredKeys(getRequiredKeys());
        return validator;
    }

    protected InServiceAgent<BaseVO, BasePO> getInServiceAgent(String beanName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(beanName), "启动参数配置有误,beanName为必填项.");
        return (InServiceAgent<BaseVO, BasePO>) applicationContext.getBean(beanName);
    }

    protected OutServiceAgent<BaseVO, BasePO> getOutServiceAgent(String beanName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(beanName), "启动参数配置有误,beanName为必填项.");
        return (OutServiceAgent<BaseVO, BasePO>) applicationContext.getBean(beanName);
    }

    public abstract ItemReader<? extends BaseVO> createInItemReader(JobRequestContext request);

    public abstract ItemProcessor<? super BaseVO, ? extends BasePO> createInItemProcessor(JobRequestContext request);

    public abstract ItemWriter<? super BasePO> createInItemWriter(JobRequestContext request);

    public abstract ItemReader<? extends BaseVO> createOutItemReader(JobRequestContext request);

    public abstract ItemProcessor<? super BaseVO, ? extends BaseVO> createOutItemProcessor(JobRequestContext request);

    public abstract ItemWriter<? super BaseVO> createOutItemWriter(JobRequestContext request);

    public abstract String[] getRequiredKeys();
}
