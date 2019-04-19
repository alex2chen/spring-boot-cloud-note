package com.kxtx.boot.client;

import java.util.List;

/**
 * Created by YT on 2017/12/11.
 */
public interface InServiceAgentV2<V extends BaseVO> {
    void translatePODataItem(V sourceData);
    int batchWriter(List<V> target);
}
