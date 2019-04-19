package com.kxtx.boot.batch.reader;

import com.google.common.collect.Maps;
import com.kxtx.boot.batch.listen.XlsFileSkipRowCallback;
import com.kxtx.boot.batch.reader.row.RowMapper;
import com.kxtx.boot.batch.reader.row.RowTokenizer;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.excel.SheetReader;
import com.kxtx.boot.excel.WorkbookProxy;
import com.kxtx.boot.batch.reader.row.CustomRowMapper;
import com.kxtx.boot.excel.support.PoiSheetReader;
import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;
import com.kxtx.boot.batch.mapping.support.CustomColumnToAliasConverter;
import com.kxtx.boot.batch.reader.row.DefaultRowTokenizer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class XlsFileItemReader<T> extends AbstractXlsFileItemReader<T> implements CustomItemReader<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileItemReader.class);
    private Workbook workbook;

    @Override
    protected SheetReader getSheet(int sheet) {
        return new PoiSheetReader(this.workbook.getSheetAt(sheet));
    }

    @Override
    protected int getSheets() {
        return this.workbook.getNumberOfSheets();
    }

    @Override
    protected void openXlsFile(Resource resource) throws Exception {
        this.workbook = WorkbookProxy.createWorkbook(resource);
    }

    @Override
    public void setServiceAgent(OutServiceAgent<BaseVO, ? extends BasePO> outServiceAgent) {
    }

    public RowMapper<BaseVO> createRowMapper(String[] colNames, String[] aliasNames, Class<? extends BaseVO> sourceType) {
        LOGGER.info("init>>>createRowMapper");
        CustomRowMapper<BaseVO> customRowMapper = new CustomRowMapper<BaseVO>();
        customRowMapper.setRowTokenizer(createLineTokenizer(colNames, aliasNames));
        BeanWrapperFieldSetMapper<BaseVO> fieldSetMapper = new BeanWrapperFieldSetMapper<BaseVO>();
        fieldSetMapper.setTargetType(sourceType);
        customRowMapper.setFieldSetMapper(fieldSetMapper);
        return customRowMapper;
    }

    private RowTokenizer createLineTokenizer(String[] colNames, String[] aliasNames) {
        DefaultRowTokenizer rowTokenizer = new DefaultRowTokenizer();
        ColumnToAliasConverter converter = new CustomColumnToAliasConverter();
        Map<String, String> maps = Maps.newHashMap();
        for (int i = 0; i < colNames.length; i++) {
            maps.put(colNames[i], aliasNames[i]);
        }
        converter.setMapping(maps);
        rowTokenizer.setConverter(converter);
        return rowTokenizer;
    }

    protected int getRowCount() {
        int count = getSheet(0).getRows();
        LOGGER.info("总行数：" + count);
        return count;
    }

    public XlsFileSkipRowCallback createRowCallback() {
        return new XlsFileSkipRowCallback() {
            @Override
            public void handleRow(SheetReader sheet, String[] row) {
                LOGGER.info("跳出数据行监听: " + sheet.getSheetName() + ",cols:" + sheet.getColumns() + ",rows:" + sheet.getRows());
                if (sheet.getRows()>30)throw new RuntimeException("已超时最大行!");
            }
        };
    }
}
