package com.kxtx.boot.excel.split;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public class SplitRequest {
    private int requestId;
    private String inputFilePath;
    private CSVSplitStrategy splitStrategy;
    private int minSplitGridSize = 50;
    private int splitGridSize;

    public SplitRequest(int requestId, String inputFilePath, CSVSplitStrategy splitStrategy, int splitGridSize, int minSplitGridSize) {
        this.requestId = requestId;
        this.inputFilePath = inputFilePath;
        this.splitStrategy = splitStrategy;
        this.minSplitGridSize = minSplitGridSize;
        this.splitGridSize = splitGridSize;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public CSVSplitStrategy getSplitStrategy() {
        return splitStrategy;
    }

    public void setSplitStrategy(CSVSplitStrategy splitStrategy) {
        this.splitStrategy = splitStrategy;
    }

    public int getMinSplitGridSize() {
        return minSplitGridSize;
    }

    public void setMinSplitGridSize(int minSplitGridSize) {
        this.minSplitGridSize = minSplitGridSize;
    }

    public int getSplitGridSize() {
        return splitGridSize;
    }

    public void setSplitGridSize(int splitGridSize) {
        this.splitGridSize = splitGridSize;
    }
}
