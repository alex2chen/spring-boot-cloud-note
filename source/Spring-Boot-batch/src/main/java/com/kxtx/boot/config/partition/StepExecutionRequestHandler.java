package com.kxtx.boot.config.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleStepHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.NoSuchStepException;
import org.springframework.batch.core.step.StepLocator;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;

/**
 * 引自StepExecutionRequestHandler
 *
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/11.
 */
@Component
public class StepExecutionRequestHandler implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(StepExecutionRequestHandler.class);
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobRepository jobRepository;
    private ApplicationContext applicationContext;
    private StepLocator stepLocator;

    @Autowired
    private StepScope stepScope;

    public StepExecution handle(SplitMessage request) {
        Long jobExecutionId = request.getJobExecutionId();
        Long stepExecutionId = request.getStepExecutionId();
        StepExecution execution = this.jobExplorer.getStepExecution(jobExecutionId, stepExecutionId);
        if (execution == null) {
            throw new NoSuchStepException("No StepExecution could be located for this request: " + request);
        } else {
            String stepName = request.getStepName();
            Step step = this.stepLocator.getStep(stepName);
            if (step == null) {
                throw new NoSuchStepException(String.format("No Step with name [%s] could be located.", new Object[]{stepName}));
            }
            try {
                StepExecution currentStepExecution = execution;
                if (shouldStart(currentStepExecution, step)) {
//                    StepSynchronizationManager.register(currentStepExecution);
                    try {
                        step.execute(currentStepExecution);
                        currentStepExecution.getExecutionContext().put("batch.executed", true);
                    } catch (JobInterruptedException e) {
                        execution.setStatus(BatchStatus.STOPPING);
                        throw e;
                    }
                    jobRepository.updateExecutionContext(execution);
                    if (currentStepExecution.getStatus() == BatchStatus.STOPPING || currentStepExecution.getStatus() == BatchStatus.STOPPED) {
                        execution.setStatus(BatchStatus.STOPPING);
                        throw new JobInterruptedException("Job interrupted by step execution");
                    }
                }
                return currentStepExecution;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected boolean shouldStart(StepExecution stepExecution, Step step) throws JobRestartException, StartLimitExceededException {
        BatchStatus stepStatus;
        if (stepExecution == null) {
            stepStatus = BatchStatus.STARTING;
        } else {
            stepStatus = stepExecution.getStatus();
        }
        if (stepStatus == BatchStatus.UNKNOWN) {
            throw new JobRestartException("Cannot restart step from UNKNOWN status.");
        }
        if ((stepStatus == BatchStatus.COMPLETED && !step.isAllowStartIfComplete()) || stepStatus == BatchStatus.ABANDONED) {
            logger.info("Step already complete or not restartable, so no action to execute: " + stepExecution);
            return false;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
        stepLocator.setBeanFactory(this.applicationContext);
        this.stepLocator = stepLocator;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public class BeanFactoryStepLocator implements StepLocator, BeanFactoryAware {
        private BeanFactory beanFactory;

        public BeanFactoryStepLocator() {
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public Step getStep(String stepName) {
            return (Step) this.beanFactory.getBean(stepName, Step.class);
        }

        @Override
        public Collection<String> getStepNames() {
            Assert.state(this.beanFactory instanceof ListableBeanFactory, "BeanFactory is not listable.");
            return Arrays.asList(((ListableBeanFactory) this.beanFactory).getBeanNamesForType(Step.class));
        }
    }
}
