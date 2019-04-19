package com.kxtx.boot.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

/**基于SXSSF
 * Created by YT on 2017/9/27.
 */
public interface SheetWriter {
    Workbook getDelegate();

    Sheet createSheet(String name);

    void addHeader(Sheet sheet, Object... fields);

    void addRow(Sheet sheet, Object... fields);

    void addRow(Sheet sheet, Iterable<? extends Object> fields);

    void flushRows(Sheet sheet) throws IOException;

    void flushRows(Sheet sheet, int rowCount) throws IOException;

    File saveFile(String fileName);
}
