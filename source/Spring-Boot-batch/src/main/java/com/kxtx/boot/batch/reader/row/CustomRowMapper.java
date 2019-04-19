package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.excel.SheetReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class CustomRowMapper<T> implements RowMapper<T> {
    private RowTokenizer rowTokenizer;
    private FieldSetMapper<T> fieldSetMapper;

    private void checkPropertiesSet() {
        Assert.notNull(this.rowTokenizer, "初始化失败,rowTokenizer缺失.");
        Assert.notNull(this.fieldSetMapper, "初始化失败,fieldSetMapper缺失.");
    }

    public T mapRow(SheetReader sheet, String[] row, int rowNum) throws Exception {
        checkPropertiesSet();
        FieldSet tokenize = this.rowTokenizer.tokenize(sheet, row);
        return this.fieldSetMapper.mapFieldSet(tokenize);
    }

    public void setRowTokenizer(RowTokenizer rowTokenizer) {
        this.rowTokenizer = rowTokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }
}
