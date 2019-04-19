package com.kxtx.boot.excel.split;

import com.kxtx.boot.excel.split.support.CSVPrinterWrapper;
import com.kxtx.boot.excel.split.support.PrintStreamWrapper;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/28.
 */
public enum CsvWriterFactory {
    csvFormat,
    streamFormat;

    public CsvWriter getCsvWriter(String filePath) {
        switch (this) {
            case csvFormat:
                return new CSVPrinterWrapper(filePath);
            case streamFormat:
                return new PrintStreamWrapper(filePath);
            default:
                return null;
        }
    }
}
