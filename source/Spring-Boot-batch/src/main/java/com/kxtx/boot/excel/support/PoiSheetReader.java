package com.kxtx.boot.excel.support;

import com.google.common.base.Strings;
import com.kxtx.boot.excel.SheetReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class PoiSheetReader implements SheetReader {
    private Sheet delegate;

    public PoiSheetReader(Sheet delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public String getSheetName() {
        return this.delegate.getSheetName();
    }

    @Override
    public String[] getHeader() {
        return this.getRow(0);
    }

    @Override
    public int getRows() {
        return this.delegate.getLastRowNum() + 1;
    }

    @Override
    public int getColumns() {
        String[] columns = this.getHeader();
        if (columns != null) {
            return columns.length;
        }
        return 0;
    }

    @Override
    public String[] getRow(int rowIndex) {
        if (rowIndex > this.delegate.getLastRowNum()) {
            return null;
        }
        Row row = this.delegate.getRow(rowIndex);
        List<String> cells = new LinkedList<String>();
        Iterator<Cell> cellIter = row.iterator();
        while (cellIter.hasNext()) {
            Cell cell = cellIter.next();
            if (rowIndex == 0 && Strings.isNullOrEmpty(cell.getStringCellValue().trim())) {
                continue;
            }
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    cells.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cells.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_STRING:
                case Cell.CELL_TYPE_BLANK:
                    cells.add(cell.getStringCellValue().trim());
                    break;
                default:
                    throw new IllegalArgumentException("不能读取未知数据表格" + cell.getCellType());
            }
        }
        return cells.toArray(new String[cells.size()]);
    }
}
