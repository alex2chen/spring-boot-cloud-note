package com.kxtx.boot.batch.listen;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public class DefaultCsvFileHeaderCallback implements FlatFileHeaderCallback {
    private String headers;

    public DefaultCsvFileHeaderCallback(String headers) {
        this.headers = headers;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(headers);
    }
}
