package com.kxtx.boot.batch.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Created by YT on 2017/10/26.
 */
public class CustomChunkListenerSupport extends ChunkListenerSupport {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomChunkListenerSupport.class);
    @Override
    public void afterChunk(ChunkContext context) {
        StepExecution stepExecution = context.getStepContext().getStepExecution();
        LOGGER.info("CustomChunkListenerSupport.afterChunk:" + stepExecution.getCommitCount() + "," + stepExecution.getReadCount() + "," + stepExecution.getWriteCount());
        stepExecution.setWriteCount(1);//TODO：可以修改batch_step_execution
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        LOGGER.info("CustomChunkListenerSupport.beforeChunk:" + context.getStepContext().getId());
        StepExecution stepExecution = context.getStepContext().getStepExecution();
        LOGGER.info("CustomChunkListenerSupport.beforeChunk:" + stepExecution.getCommitCount() + "," + stepExecution.getWriteCount());
    }

    @Override
    public void afterChunkError(ChunkContext context) {

    }
}
