package com.kxtx.boot.excel.split;

import java.util.Arrays;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public class SplitResponse {
    private boolean isSuc;
    private String outputFileRegular;
    private String[] outputFile;
    private String header;
    private int rowCount;

    public SplitResponse() {
        isSuc = false;
    }

    public SplitResponse(boolean isSuc, String outputFileRegular, String[] outputFile, String header, int rowCount) {
        this.isSuc = isSuc;
        this.outputFileRegular = outputFileRegular;
        this.outputFile = outputFile;
        this.header = header;
        this.rowCount = rowCount;
    }

    public boolean isSuc() {
        return isSuc;
    }

    public void setSuc(boolean suc) {
        isSuc = suc;
    }

    public String getOutputFileRegular() {
        return outputFileRegular;
    }

    public void setOutputFileRegular(String outputFileRegular) {
        this.outputFileRegular = outputFileRegular;
    }

    public String[] getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String[] outputFile) {
        this.outputFile = outputFile;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public String toString() {
        return "CSVSplitResponse{" +
                "isSuc=" + isSuc +
                ", outputFileRegular='" + outputFileRegular + '\'' +
                ", outputFile=" + Arrays.toString(outputFile) +
                ", header='" + header + '\'' +
                ", rowCount=" + rowCount +
                '}';
    }
}
