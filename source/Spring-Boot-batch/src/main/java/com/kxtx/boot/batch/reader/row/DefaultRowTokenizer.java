package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;
import com.kxtx.boot.excel.SheetReader;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class DefaultRowTokenizer implements RowTokenizer {
    private FieldSetFactory fieldSetFactory;
    private ColumnToAliasConverter converter;
    private boolean useColHeader = true;

    public DefaultRowTokenizer() {
        fieldSetFactory = new DefaultFieldSetFactory();
    }

    @Override
    public FieldSet tokenize(SheetReader sheet, final String[] row) {
        String[] values = new String[sheet.getColumns()];
        System.arraycopy(row, 0, values, 0, row.length);
        if (this.useColHeader) {
            checkPropertiesSet();
            String[] names = sheet.getHeader();
            for (int i = 0; i < names.length; i++) {
                names[i] = this.converter.toAlias(names[i]);
            }
            return this.fieldSetFactory.create(values, names);
        } else {
            return this.fieldSetFactory.create(values);
        }
    }

    private void checkPropertiesSet() {
        Assert.notNull(this.converter, "初始化失败,converter缺失.");
    }

    public void setConverter(ColumnToAliasConverter converter) {
        this.converter = converter;
    }

    public void setUseColHeader(boolean useColHeader) {
        this.useColHeader = useColHeader;
    }
}

