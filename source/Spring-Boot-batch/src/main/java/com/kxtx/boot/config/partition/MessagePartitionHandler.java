package com.kxtx.boot.config.partition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * ref MessageChannelPartitionHandler
 *
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/10.
 */
@Component
public class MessagePartitionHandler implements PartitionHandler, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MessagePartitionHandler.class);
    private String stepName = ImportPartitionJobConfiguration.STEP_SLAVE_NAME;
    private boolean pollRepositoryForResults = false;
    private int gridSize = 1;
    @Autowired
    private MessagingTemplate messagingGateway;
    @Autowired
    public JobExplorer jobExplorer;

    @Override
    public Collection<StepExecution> handle(StepExecutionSplitter stepSplitter, StepExecution masterStepExecution) throws Exception {

        Set split = stepSplitter.split(masterStepExecution, this.gridSize);
        if (CollectionUtils.isEmpty(split)) {
            return null;
        } else {
            ArrayList result = new ArrayList(split.size());
            int count = 0;
            SplitMessage request;
            Iterator message = split.iterator();
            while (message.hasNext()) {
                StepExecution stepExecution = (StepExecution) message.next();
                result.add(stepExecution);
                request = this.createMessage(count++, split.size(), this.stepName, stepExecution.getJobExecutionId(), stepExecution.getId());
                if (logger.isDebugEnabled()) {
                    logger.info("Sending request: " + request);
                }
                this.messagingGateway.send(request);
            }
//            if (this.pollRepositoryForResults) {
//                return this.pollReplies(masterStepExecution, split);
//            } else {
//                return this.receiveReplies(this.replyChannel);
//            }
            return result;
        }
    }

    private SplitMessage createMessage(int seq, int size, String stepName, Long jobExecutionId, Long stepExecutionId) {
        return new SplitMessage(seq, size, jobExecutionId, stepExecutionId, stepName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.stepName, "A step name must be provided for the remote workers.");
        Assert.state(this.messagingGateway != null, "The MessagingOperations must be set");
    }
}
