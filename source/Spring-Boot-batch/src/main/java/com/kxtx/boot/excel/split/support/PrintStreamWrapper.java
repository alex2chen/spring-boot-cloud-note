package com.kxtx.boot.excel.split.support;

import com.kxtx.boot.excel.split.CsvWriter;
import org.apache.commons.io.IOUtils;

import java.io.PrintStream;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/28.
 */
public class PrintStreamWrapper implements CsvWriter {
    private PrintStream writer;

    public PrintStreamWrapper(String filePath) {
        init(filePath);
    }

    private void init(String filePath) {
        try {
            writer = new PrintStream(filePath, OUT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(String col) {
        writer.print(col);
    }

    @Override
    public void println() {
        writer.println();
    }

    @Override
    public void println(Object... values) {
        for (Object item : values) {
            writer.print(item);
        }
        println();
    }

    @Override
    public void flush() {
        writer.flush();
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(writer);
    }
}
