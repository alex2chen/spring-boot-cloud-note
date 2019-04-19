package com.kxtx.boot.excel.support;

import com.google.common.base.Preconditions;
import com.kxtx.boot.excel.SheetWriter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/27
 */
public class PoiSheetWriter implements SheetWriter {
    private static final Logger LOG = LoggerFactory.getLogger(PoiSheetWriter.class);
    private Workbook delegate;

    public PoiSheetWriter(Workbook delegate) {
        this.delegate = delegate;
    }

    @Override
    public Sheet createSheet(String name) {
        Assert.hasLength(name, "初始化失败,name为必填项.");
        Preconditions.checkArgument(!getAllSheetNames().contains(name), "Sheet name is already existed.");
        return delegate.createSheet(name);
    }

    private List<String> getAllSheetNames() {
        List<String> sheets = newArrayList();
        for (int i = 0; i < delegate.getNumberOfSheets(); i++) {
            sheets.add(delegate.getSheetName(i));
        }
        return sheets;
    }

    @Override
    public void addHeader(Sheet sheet, Object... fields) {
        Row row = sheet.createRow(0);
        createCells(row, Arrays.asList(fields));
    }

    public void addRow(Sheet sheet, Object... fields) {
        this.addRow(sheet, Arrays.asList(fields));
    }

    @Override
    public void addRow(Sheet sheet, Iterable<? extends Object> fields) {
        Row row;
        if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
            row = sheet.createRow(0);
        } else {
            row = sheet.createRow(sheet.getLastRowNum() + 1);
        }
        //createCells(row, fields);
        createCellsTextFormat(row, fields);
    }

    private void createCellsTextFormat(Row row, Iterable<? extends Object> fields) {
        CellStyle cellStyle = delegate.createCellStyle();
        DataFormat format = delegate.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));//文本型
        int i = 0;
        for (Object o : fields) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            if (o != null) {
                cell.setCellValue(o.toString());
            }
            i++;
        }
    }

    @Deprecated
    private void createCells(Row row, Iterable<? extends Object> fields) {
        int i = 0;
        for (Object o : fields) {
            Cell cell = row.createCell(i);
            if (o != null) {
                if (o instanceof Boolean)
                    cell.setCellValue((Boolean) o);
                else if (o instanceof Calendar)
                    cell.setCellValue((Calendar) o);
                else if (o instanceof Date)
                    cell.setCellValue((Date) o);
                else if (o instanceof Double)
                    cell.setCellValue((Double) o);
                else if (o instanceof Number)
                    cell.setCellValue(((Number) o).doubleValue());
                else
                    cell.setCellValue(o.toString());
            }
            i++;
        }
    }

    @Override
    public void flushRows(Sheet sheet) throws IOException {
        this.flushRows(sheet, 0);
    }

    @Override
    public void flushRows(Sheet sheet, int rowCount) throws IOException {
        if (sheet instanceof SXSSFSheet) {
            ((SXSSFSheet) sheet).flushRows(rowCount);
        } else {
            LOG.warn("No use flushRows,sheet type is not flushRows.");
        }
    }

    @Override
    public File saveFile(String fileName) {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            File file = resourceLoader.getResource(fileName).getFile();
            FileOutputStream out = new FileOutputStream(file);
            delegate.write(out);
            out.close();
            delegate.close();
        } catch (IOException e) {
            LOG.error("输出文件发生错误.", e);
            throw new RuntimeException(e);
        }
        return new File(fileName);
    }

    @Override
    public Workbook getDelegate() {
        return delegate;
    }
}
