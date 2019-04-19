package com.kxtx.boot.batch.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Created by YT on 2018/1/12.
 */
public class CustomChunkListener implements ChunkListener {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomChunkListener.class);
    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        LOGGER.info("beforeChunk");
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        LOGGER.info("afterChunk:");
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        LOGGER.info("afterChunkError"+chunkContext.getStepContext().getStepExecution());
        chunkContext.getStepContext().setAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY,"");
        LOGGER.info("afterChunkError"+chunkContext.getStepContext());
    }
}
