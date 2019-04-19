package com.kxtx.boot.batch.listen;

import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by YT on 2017/10/26.
 */
@Deprecated
public class CustomItemListenerImpl extends ItemListenerSupport<BaseVO, BasePO> {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomItemListenerImpl.class);
    @Override
    public void beforeWrite(List<? extends BasePO> target) {
        target = target.stream().limit(1).collect(Collectors.toList());
        LOGGER.info("CustomItemListenerImpl.beforeWrite:" + target.size());
    }
}
