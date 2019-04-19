package com.kxtx.boot.batch.reader;

import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import org.springframework.batch.item.ItemReader;

/**
 * Created by YT on 2017/9/21.
 */
public interface CustomItemReader<T> extends ItemReader<T> {
    void setServiceAgent(OutServiceAgent<BaseVO, ? extends BasePO> outServiceAgent);
}
