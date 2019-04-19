package com.kxtx.boot.config.partition;

import java.io.Serializable;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/11.
 */
public class SplitMessage implements Serializable {
    private Integer sequenceNumber;
    private Integer sequenceSize;

    private Long jobExecutionId;
    private Long stepExecutionId;
    private String stepName;


    public SplitMessage() {
    }

    public SplitMessage(Integer sequenceNumber, Integer sequenceSize, Long jobExecutionId, Long stepExecutionId, String stepName) {
        this.sequenceNumber = sequenceNumber;
        this.sequenceSize = sequenceSize;
        this.jobExecutionId = jobExecutionId;
        this.stepExecutionId = stepExecutionId;
        this.stepName = stepName;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getSequenceSize() {
        return sequenceSize;
    }

    public void setSequenceSize(Integer sequenceSize) {
        this.sequenceSize = sequenceSize;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    @Override
    public String toString() {
        return "SplitMessage{" +
                "sequenceNumber=" + sequenceNumber +
                ", sequenceSize=" + sequenceSize +
                ", jobExecutionId=" + jobExecutionId +
                ", stepExecutionId=" + stepExecutionId +
                ", stepName='" + stepName + '\'' +
                '}';
    }
}
