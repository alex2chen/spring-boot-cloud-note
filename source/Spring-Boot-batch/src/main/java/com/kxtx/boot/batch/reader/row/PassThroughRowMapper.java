package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.excel.SheetReader;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class PassThroughRowMapper implements RowMapper<String[]> {

    public String[] mapRow(SheetReader sheet, String[] row, int rowNum) throws Exception {
        return row;
    }
}
