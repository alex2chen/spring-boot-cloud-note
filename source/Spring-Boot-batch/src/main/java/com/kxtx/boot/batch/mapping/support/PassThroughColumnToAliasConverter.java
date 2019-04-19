package com.kxtx.boot.batch.mapping.support;

import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;

import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class PassThroughColumnToAliasConverter implements ColumnToAliasConverter {
    @Override
    public String toAlias(String column) {
        return column;
    }

    @Override
    public String toColumn(String alias) {
        return alias;
    }

    @Override
    public void setMapping(Map<String, String> mapping) {
    }
}
