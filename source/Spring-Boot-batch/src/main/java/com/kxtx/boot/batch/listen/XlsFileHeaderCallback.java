package com.kxtx.boot.batch.listen;

import com.kxtx.boot.excel.SheetWriter;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/27
 */
public interface XlsFileHeaderCallback {
    void writeHeader(SheetWriter writer, Sheet sheet) throws IOException;
}
