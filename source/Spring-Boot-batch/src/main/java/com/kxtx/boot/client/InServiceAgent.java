package com.kxtx.boot.client;

import java.util.List;

/**
 * 导入业务抽象层
 * Created by YT on 2017/9/20.
 */
public interface InServiceAgent<V extends BaseVO, P extends BasePO> {
    void translatePODataItem(V sourceData, P target);

    void writer(P target);

    int batchWriter(List<P> target);
}
