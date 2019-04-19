package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.excel.SheetReader;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public interface RowTokenizer {
    FieldSet tokenize(SheetReader sheet, String[] row);
}
