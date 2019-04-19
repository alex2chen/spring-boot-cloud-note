package com.kxtx.boot.batch.writer;

import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.client.BasePO;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public class CsvFileItemWriter<T> extends FlatFileItemWriter<T> implements CustomItemWriter<T> {

    public DelimitedLineAggregator<BaseVO> createLineAggregator(String[] colNames) {
        DelimitedLineAggregator<BaseVO> lineAggregator = new DelimitedLineAggregator<BaseVO>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<BaseVO> fieldExtractor = new BeanWrapperFieldExtractor<BaseVO>();
        fieldExtractor.setNames(colNames);
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }

    @Override
    public void setServiceAgent(InServiceAgent<BaseVO, ? extends BasePO> inServiceAgent) {
        throw new RuntimeException("非法执行路径.");
    }
}
