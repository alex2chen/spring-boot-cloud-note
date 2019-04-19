package com.kxtx.boot.batch;

import org.springframework.batch.core.Job;

/**
 * Created by YT on 2017/9/19.
 */
@Deprecated
public interface JobRegistry {
    Job getJob(JobExecStrategy jobExecStrategy, String jobName);

    Job createDynamicJob(JobType jobType, String jobName);

    enum JobType {
        Import,
        Export
    }
}
