package com.kxtx.boot.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YT on 2017/12/11.
 */
public interface OutServiceAgentV2<V extends BaseVO> {
    int getItemCount(Map parameters);

    List<V> pagingReader(HashMap parameters);
}