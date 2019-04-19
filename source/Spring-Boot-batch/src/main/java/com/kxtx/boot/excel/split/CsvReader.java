package com.kxtx.boot.excel.split;

import com.google.common.collect.Iterators;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public class CsvReader {
    public SplitResponse read(SplitRequest request) {
        try {
            CSVParser reader = getCSVParser(request.getInputFilePath());
            SplitResponse response = new SplitResponse();
            Iterator<CSVRecord> iterator = reader.iterator();
            SplitHolder splitHolder = new SplitHolder(request, response);
            processRows(iterator, splitHolder);
            IOUtils.closeQuietly(reader);
            return response;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void processRows(Iterator<CSVRecord> iterator, SplitHolder splitHolder) {
        splitHolder.initializing();
        //Object[] cols = null;
        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            splitHolder.println(Iterators.toArray(record.iterator(), Object.class));
        }
        splitHolder.disposable();
    }

    public static CSVParser getCSVParser(String filePath) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withSkipHeaderRecord(false);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "GBK");
        return new CSVParser(isr, format);
    }
}
