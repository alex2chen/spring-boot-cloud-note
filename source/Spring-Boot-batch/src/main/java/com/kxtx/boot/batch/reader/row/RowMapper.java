package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.excel.SheetReader;

/**
 * Created by YT on 2017/9/25.
 */
public interface RowMapper<T> {
    T mapRow(SheetReader sheet, String[] row, int rowNum) throws Exception;
}
