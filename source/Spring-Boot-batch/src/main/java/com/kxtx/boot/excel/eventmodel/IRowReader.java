package com.kxtx.boot.excel.eventmodel;

import java.util.List;

/**
 * Created by YT on 2017/12/18.
 */
public interface IRowReader {
    void getRows(int sheetIndex, int curRow, List<String> rowlist);
}
