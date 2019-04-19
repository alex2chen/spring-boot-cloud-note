package com.kxtx.boot.batch.listen;

import com.kxtx.boot.excel.SheetReader;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public interface XlsFileSkipRowCallback {
    void handleRow(SheetReader sheet, String[] row);
}
