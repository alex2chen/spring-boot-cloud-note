package com.kxtx.boot.batch.admix;

import com.kxtx.boot.batch.writer.ItemWriterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/23
 */
@Deprecated
public class ExportTypeDecider implements JobExecutionDecider {
    private static final Logger LOG = LoggerFactory.getLogger(ExportTypeDecider.class);
    private ItemWriterFactory type;

    public ExportTypeDecider(ItemWriterFactory type) {
        this.type = type;
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        LOG.info("decide");
        if (type == ItemWriterFactory.Xls) {
            return new FlowExecutionStatus(ItemWriterFactory.Xls.toString());
        }
        return new FlowExecutionStatus(ItemWriterFactory.Csv.toString());
    }
}
