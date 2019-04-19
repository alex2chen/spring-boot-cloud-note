package com.kxtx.boot.excel.eventmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by YT on 2017/12/18.
 */
public class RowReader implements IRowReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(RowReader.class);

    @Override
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        // TODO Auto-generated method stub

        StringBuilder stringBuilder = new StringBuilder().append(curRow).append(" ");
        for (int i = 0; i < rowlist.size(); i++) {
            stringBuilder.append(rowlist.get(i)).append(" ");
        }
        LOGGER.info(stringBuilder.toString());
    }
}
