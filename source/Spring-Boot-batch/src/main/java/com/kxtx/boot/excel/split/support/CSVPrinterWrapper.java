package com.kxtx.boot.excel.split.support;

import com.kxtx.boot.excel.split.CsvWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public class CSVPrinterWrapper implements CsvWriter {
    private CSVPrinter writer;

    public CSVPrinterWrapper(String filePath) {
        init(filePath);
    }

    private void init(String filePath) {
        try {
            CSVFormat format = CSVFormat.INFORMIX_UNLOAD_CSV.withRecordSeparator("\n");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), OUT_CHARSET);
            writer = new CSVPrinter(osw, format);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(String col) {
        try {
            writer.print(col);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void println() {
        try {
            writer.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void println(Object... values) {
        try {
            writer.printRecord(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(writer);
    }
}
