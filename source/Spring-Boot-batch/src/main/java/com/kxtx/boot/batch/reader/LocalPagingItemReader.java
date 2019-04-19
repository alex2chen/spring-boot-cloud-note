package com.kxtx.boot.batch.reader;

import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.client.BasePO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.IbatisPagingItemReader;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */

public class LocalPagingItemReader extends AbstractPagingItemReader<BaseVO> implements CustomItemReader<BaseVO> {
    private static final Logger LOG = LoggerFactory.getLogger(LocalPagingItemReader.class);
    private OutServiceAgent<? extends BaseVO, ? extends BasePO> outServiceAgent;
    private Map<String, Object> parameterValues;

    public LocalPagingItemReader() {
        this.setName(ClassUtils.getShortName(IbatisPagingItemReader.class));
    }

    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    @Override
    public void setServiceAgent(OutServiceAgent<BaseVO, ? extends BasePO> outServiceAgent) {
        this.outServiceAgent = outServiceAgent;
    }

    @Override
    protected void doReadPage() {
        HashMap parameters = new HashMap();
        if (this.parameterValues != null) {
            parameters.putAll(this.parameterValues);
        }
        parameters.put("_page", Integer.valueOf(this.getPage()));
        parameters.put("_pagesize", Integer.valueOf(this.getPageSize()));
        parameters.put("_skiprows", Integer.valueOf(this.getPage() * this.getPageSize()));
        if (this.results == null) {
            this.results = new CopyOnWriteArrayList();
        } else {
            this.results.clear();
        }
        List<? extends BaseVO> datas = this.outServiceAgent.pagingReader(parameters);
        if (datas != null && !datas.isEmpty()) {
            this.results.addAll(datas);
        }
        LOG.info("doReadPage:" + this.results.size());
    }

    @Override
    protected void doJumpToPage(int i) {
        LOG.info("暂未支持.");
    }
}
