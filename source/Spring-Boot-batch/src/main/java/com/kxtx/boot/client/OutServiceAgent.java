package com.kxtx.boot.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出业务抽象层
 * Created by YT on 2017/9/20.
 */
public interface OutServiceAgent<V extends BaseVO, T extends BasePO> {
    int getItemCount(Map parameters);

    List<V> pagingReader(HashMap parameters);

    V partTranslateDataItem(V target);
}
