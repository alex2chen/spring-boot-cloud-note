package com.kxtx.boot.batch.reader;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.kxtx.boot.batch.reader.row.CsvLineMapper;
import com.kxtx.boot.batch.reader.row.CustomDelimitedLineTokenizer;
import com.kxtx.boot.batch.mapping.ColumnToAliasConverter;
import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.OutServiceAgent;
import com.kxtx.boot.batch.mapping.support.CustomColumnToAliasConverter;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/21
 */
public class CsvFileItemReader<T> extends FlatFileItemReader<T> implements CustomItemReader<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileItemReader.class);

    public LineMapper<BaseVO> createLineMapper(String[] colNames, String[] aliasNames, Class<? extends BaseVO> sourceType) {
        LOGGER.info("init>>>createLineMapper");
        CsvLineMapper lineMapper = new CsvLineMapper();
        lineMapper.setLineTokenizer(createLineTokenizer(colNames, aliasNames));

        BeanWrapperFieldSetMapper<BaseVO> fieldSetMapper = new BeanWrapperFieldSetMapper<BaseVO>();
        fieldSetMapper.setTargetType(sourceType);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    private LineTokenizer createLineTokenizer(String[] colNames, String[] aliasNames) {
        CustomDelimitedLineTokenizer lineTokenizer = new CustomDelimitedLineTokenizer();
        lineTokenizer.setNames(colNames);
        ColumnToAliasConverter converter = new CustomColumnToAliasConverter();
        Map<String, String> maps = Maps.newHashMap();
        for (int i = 0; i < colNames.length; i++) {
            maps.put(colNames[i], aliasNames[i]);
        }
        converter.setMapping(maps);
        lineTokenizer.setConverter(converter);
        return lineTokenizer;
    }

    public int getRowCount(Resource resource) {
        Stopwatch stopWatch = Stopwatch.createStarted();
        int rowCount = 0;
        FileInputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(resource.getFile());
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            BufferedReader reader = IOUtils.toBufferedReader(streamReader);
            while (reader.readLine() != null) rowCount++;
            LOGGER.info("读取导入文件：" + resource.getFilename() + "总行数：" + rowCount + "耗时：" + stopWatch);
        } catch (IOException ex) {
            throw new RuntimeException("读取导入文件失败");
        } finally {
            if (inputStream != null) IOUtils.closeQuietly(inputStream);
        }
        return rowCount;
    }

    @Override
    public void setServiceAgent(OutServiceAgent<BaseVO, ? extends BasePO> inServiceAgent) {
        throw new RuntimeException("非法执行路径.");
    }
}
