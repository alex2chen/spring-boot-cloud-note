package com.kxtx.boot.batch.reader;

import com.google.common.collect.Maps;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;

/**
 * Csv:从csv文件中读取
 * Xls:从excel文件中读取
 * Local:读取本应用读取
 * Remote:从远程应用上读取
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public enum ItemReaderFactory {
    Csv,
    Xls,
    Local,
    Remote;

    public CustomItemReader<? extends BaseVO> create(JobRequestContext request, OutServiceAgent<BaseVO, BasePO> agent) {
        switch (this) {
            case Csv:
                CsvFileItemReader<BaseVO> readerCsv = new CsvFileItemReader<BaseVO>();
                readerCsv.setLinesToSkip(1);
                ClassPathResource resource = new ClassPathResource(request.getIn().getInputFilePath());
                readerCsv.getRowCount(resource);
                //更新总行数
                readerCsv.setResource(resource);
                readerCsv.setLineMapper(readerCsv.createLineMapper(request.getIn().getInputCols(), request.getIn().getInputAlias(), request.getIn().getSourceType()));
                return readerCsv;
            case Local:
                LocalPagingItemReader readerLocal = new LocalPagingItemReader();
                readerLocal.setServiceAgent(agent);
                Map<String, Object> parameterValues = Maps.newHashMap();
                parameterValues.put("id", 1);
                readerLocal.setParameterValues(parameterValues);
                readerLocal.setPageSize(2);
                readerLocal.setMaxItemCount(20);
                return readerLocal;
            case Xls:
                XlsFileItemReader<BaseVO> readerXls = new XlsFileItemReader<BaseVO>();
                readerXls.setLinesToSkip(1);
                readerXls.setResource(new ClassPathResource(request.getIn().getInputFilePath()));
                readerXls.setRowMapper(readerXls.createRowMapper(request.getIn().getInputCols(), request.getIn().getInputAlias(), request.getIn().getSourceType()));
                readerXls.setSkipRowsCallback(readerXls.createRowCallback());
                readerXls.afterPropertiesSet();
                //readerXls.getRowCount();
                return readerXls;
            default:
                throw new RuntimeException("无效参数");
        }

    }
}
