package com.kxtx.boot.excel.split;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/28.
 */
public interface CsvWriter {

    static final String OUT_CHARSET = "GBK";//"UTF-8";

    void print(String col);

    void println();

    void println(Object... values);

    void flush();

    void close();
}
