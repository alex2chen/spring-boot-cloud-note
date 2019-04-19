package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.util.Assert;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class CustomDelimitedLineTokenizer extends DelimitedLineTokenizer {
    private ColumnToAliasConverter converter;
    private FieldSetFactory fieldSetFactory;

    public CustomDelimitedLineTokenizer() {
        fieldSetFactory = new DefaultFieldSetFactory();
    }

    @Override
    public FieldSet tokenize(String line) {
        Assert.notNull(converter, "执行失败,请检查配置项converter!");
        FieldSet tokenize = super.tokenize(line);
        String[] newNames = tokenize.getNames();
        for (int i = 0; i < newNames.length; i++) {
            newNames[i] = this.converter.toAlias(newNames[i]);
        }
        return fieldSetFactory.create(tokenize.getValues(), newNames);
    }


    public void setConverter(ColumnToAliasConverter converter) {
        this.converter = converter;
    }
}
