package com.kxtx.boot.batch.mapping;

import java.util.Map;

/**
 * colunm:(文件中的列)-key
 * alias: (实体中的属性) -value
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public interface ColumnToAliasConverter {
    String toAlias(String column);

    String toColumn(String alias);

    void setMapping(Map<String, String> mapping);
}
