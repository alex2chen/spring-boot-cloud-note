package com.kxtx.boot.excel.split;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.kxtx.boot.excel.split.support.CSVPrinterWrapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;


/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public class SplitHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SplitHolder.class);
    private String outFilePath;
    private String outFileName;
    private String outFileExdName = "csv";
    private int currentRowIndex = 0;
    private List<CsvWriter> outputFiles;
    private SplitRequest request;
    private SplitResponse response;
    private boolean hasNeedPrintHeader = true;
    private boolean hasNeedPrintId = false;
    private Object[] header;

    public SplitHolder(SplitRequest request, SplitResponse response) {
        this.request = request;
        this.response = response;
    }

    private synchronized void addOutputFiles(String newFile) {
        if (!outputFiles.contains(newFile)) {
            CsvWriter fileWriter = CsvWriterFactory.csvFormat.getCsvWriter(newFile);
            if (outputFiles.size() >= 1 && hasNeedPrintHeader) {
                fileWriter.println(header);
            }
            outputFiles.add(fileWriter);
        }
    }

    private CsvWriter getWriter(boolean isNewLine) {
        String file = null;
        int outputPosition = 0;
        switch (request.getSplitStrategy()) {
            case no:
                if (currentRowIndex == 0) {
                    file = getFilename(0);
                    addOutputFiles(file);
                }
                return outputFiles.get(0);
            case constant:
                int splitGridSize = request.getSplitGridSize();
                outputPosition = currentRowIndex / splitGridSize;
                if (currentRowIndex % splitGridSize == 0 && outputFiles.size() == outputPosition) {
                    file = getFilename(outputPosition);
                    addOutputFiles(file);
                    LOGGER.info("创建文件:{}", file);
                    if (outputPosition >= 1) {
                        outputFiles.get(outputPosition - 1).flush();
                        outputFiles.get(outputPosition - 1).close();
                    }
                }
                return outputFiles.get(outputPosition);

            case average:
                outputPosition = currentRowIndex / request.getMinSplitGridSize();
                int batchIndex = currentRowIndex % request.getMinSplitGridSize();
                if (outputPosition < request.getSplitGridSize()) {
                    if (batchIndex == 0) {
                        if (outputFiles.size() == outputPosition) {
                            file = getFilename(outputPosition);
                            addOutputFiles(file);
                            LOGGER.info("创建文件:{}", file);
                        }

                    }
                }
                CsvWriter stream = outputFiles.get(outputPosition % request.getSplitGridSize());
                if (batchIndex == 0 && isNewLine) {
                    stream.flush();
                }
                return stream;
            default:
                throw new RuntimeException("拆分文件出错，未知请求参数[SplitStrategy]");
        }
    }

    public void print(String col) {
        //TODO:
    }

    public void println() {
        println(null);
    }

    public void println(Object[] cols) {
        try {
            CsvWriter outPrint = getWriter(true);
            if (cols == null) {
                outPrint.println();
                return;
            }
            if (hasNeedPrintId) {
                List list = Lists.newArrayList(cols);
                list.add(0, currentRowIndex == 0 ? "id" : currentRowIndex + 1);
                cols = list.toArray();
            }
            if (currentRowIndex == 0) {
                header = cols;
                if (!hasNeedPrintHeader) {
                    return;
                }
            }
            outPrint.println(cols);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            currentRowIndex++;
        }
    }


    public void close() {
        if (outputFiles != null) {
            outputFiles.forEach(x -> x.close());
        }
    }

    private String getFilename(String regix) {
        String file = String.format("%s/%s%s.%s", outFilePath, outFileName, regix, outFileExdName);
        return file;
    }

    private String getFilename(int index) {
        return getFilename("" + index);
    }

    public void initializing() {
        String inputFileParentPath = FilenameUtils.getFullPath(request.getInputFilePath());
        outFilePath = String.format("%s/%s/%s", inputFileParentPath, request.getRequestId(), System.currentTimeMillis());
        new File(outFilePath).mkdirs();
        outFileName = FilenameUtils.getBaseName(request.getInputFilePath());
        outputFiles = Lists.newLinkedList();
        hasNeedPrintHeader = true;
        hasNeedPrintId = request.getSplitStrategy() == CSVSplitStrategy.no ? false : true;
    }

    public void disposable() {
        response.setSuc(true);
        response.setHeader(Joiner.on(",").join(header));
        response.setRowCount(currentRowIndex);
        response.setOutputFileRegular(getFilename("*"));
        //response.setOutputFile();
        close();
    }
}
