package com.kxtx.boot.batch.admix;

import com.kxtx.boot.batch.JobExecStrategy;
import com.kxtx.boot.batch.JobRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
@Deprecated
public class JobRegistryImpl implements JobRegistry {
    private static final Logger logger = LoggerFactory.getLogger(JobRegistryImpl.class);
    @Autowired
    private JobBuilderFactory jobBuilders;
    @Autowired
    private StepBuilderFactory stepBuilders;
    @Override
    public Job getJob(JobExecStrategy jobExecStrategy, String jobName) {
        if (jobExecStrategy==JobExecStrategy.Static){

        }
        return null;
    }

    @Override
    public Job createDynamicJob(JobType jobType, String jobName) {
        return null;
    }
}
