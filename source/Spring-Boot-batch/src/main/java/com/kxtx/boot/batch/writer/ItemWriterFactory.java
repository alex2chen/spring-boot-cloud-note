package com.kxtx.boot.batch.writer;

import com.google.common.base.Joiner;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.batch.listen.DefaultCsvFileHeaderCallback;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.excel.SheetWriter;
import com.kxtx.boot.batch.listen.XlsFileHeaderCallback;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * Csv:输出csv文件
 * Xls:输出excel文件
 * Local:输出到当前应用
 * Remote:输出到当前远程应用
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public enum ItemWriterFactory {
    Csv,
    Xls,
    Local,
    Remote;

    public CustomItemWriter<?> create(JobRequestContext request, InServiceAgent<BaseVO, BasePO> agent, ResourceLoader resourceLoader) {
        switch (this) {
            case Csv:
                CsvFileItemWriter<BaseVO> writerCvs = new CsvFileItemWriter<BaseVO>();
                writerCvs.setResource(resourceLoader.getResource(request.getOut().getOutFilePath()));
                writerCvs.setLineAggregator(writerCvs.createLineAggregator(request.getOut().getOutAlias()));
                writerCvs.setHeaderCallback(new DefaultCsvFileHeaderCallback(Joiner.on(',').skipNulls().join(request.getOut().getOutCols())));
                writerCvs.setShouldDeleteIfEmpty(true);
                writerCvs.setShouldDeleteIfExists(true);
                return writerCvs;
            case Local:
                LocalBatchItemWriter<BasePO> writer = new LocalBatchItemWriter<BasePO>();
                writer.setServiceAgent(agent);
                return writer;
            case Xls:
                XlsFileItemWriter<BaseVO> writerXls = new XlsFileItemWriter<BaseVO>();
                writerXls.setResource(resourceLoader.getResource(request.getOut().getOutFilePath()));
                writerXls.setFieldExtractor(writerXls.createFieldExtractor(request.getOut().getOutAlias()));
                writerXls.setHeaderCallback(new XlsFileHeaderCallback() {
                    @Override
                    public void writeHeader(SheetWriter wt, Sheet sheet) throws IOException {
                        wt.addHeader(sheet, request.getOut().getOutCols());
                    }
                });
                writerXls.setShouldDeleteIfEmpty(true);
                writerXls.setShouldDeleteIfExists(true);
                return writerXls;
            default:
                throw new RuntimeException("无效参数");
        }

    }

}
