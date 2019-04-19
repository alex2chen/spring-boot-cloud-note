package com.kxtx.boot.batch.writer;

import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.BasePO;
import org.springframework.batch.item.ItemWriter;

/**
 * Created by YT on 2017/9/21.
 */
public interface CustomItemWriter<T> extends ItemWriter<T> {
    void setServiceAgent(InServiceAgent<BaseVO, ? extends BasePO> inServiceAgent);
}
