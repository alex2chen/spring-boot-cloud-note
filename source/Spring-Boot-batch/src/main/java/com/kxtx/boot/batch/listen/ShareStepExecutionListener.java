package com.kxtx.boot.batch.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/23
 */
@Deprecated
public class ShareStepExecutionListener implements StepExecutionListener {
    private static final Logger LOG = LoggerFactory.getLogger(ShareStepExecutionListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOG.info("goto Step:" + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOG.info("after Step:" + stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
    }
}
