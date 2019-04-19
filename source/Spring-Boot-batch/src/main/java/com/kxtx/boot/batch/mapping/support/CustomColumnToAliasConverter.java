package com.kxtx.boot.batch.mapping.support;

import com.google.common.collect.Maps;
import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class CustomColumnToAliasConverter implements ColumnToAliasConverter {

    private Map<String, String> mapping;

    public CustomColumnToAliasConverter() {
        mapping = Maps.newHashMap();
    }

    @Override
    public String toAlias(String column) {
        if (this.mapping.containsKey(column)) {
            return this.mapping.get(column);
        }
        return column;
    }

    @Override
    public String toColumn(String alias) {
        if (this.mapping.containsValue(alias)) {
            for (Map.Entry<String, String> entry : this.mapping.entrySet()) {
                if (ObjectUtils.nullSafeEquals(alias, entry.getValue())) {
                    return entry.getKey();
                }
            }
        }
        return alias;
    }

    @Override
    public void setMapping(Map<String, String> mapping) {
        this.mapping.clear();
        this.mapping = mapping;
    }
}
