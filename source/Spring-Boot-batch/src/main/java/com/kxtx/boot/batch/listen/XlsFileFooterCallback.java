package com.kxtx.boot.batch.listen;


import com.kxtx.boot.excel.SheetWriter;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

/**
 * Created by YT on 2017/9/27.
 */
public interface XlsFileFooterCallback {
    void writeFooter(SheetWriter writer, Sheet sheet) throws IOException;
}
