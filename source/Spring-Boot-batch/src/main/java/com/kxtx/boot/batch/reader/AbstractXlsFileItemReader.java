package com.kxtx.boot.batch.reader;

import com.kxtx.boot.batch.listen.XlsFileSkipRowCallback;
import com.kxtx.boot.batch.reader.row.RowMapper;
import com.kxtx.boot.excel.SheetReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public abstract class AbstractXlsFileItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements ResourceAwareItemReaderItemStream<T>, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractXlsFileItemReader.class);
    private Resource resource;
    private int linesToSkip = 0;
    private int currentRow = 0;
    private int currentSheet = 0;
    private RowMapper<T> rowMapper;
    private XlsFileSkipRowCallback rowsCallback;
    private boolean noInput = false;

    public AbstractXlsFileItemReader() {
        super();
        this.setName(ClassUtils.getShortName(this.getClass()));
    }

    @Override
    protected T doRead() throws Exception {
        if (this.noInput) {
            return null;
        }
        final SheetReader sheet = this.getSheet(this.currentSheet);
        String[] headers = sheet.getHeader();
        final String[] row = this.readRow(sheet);
        if (!ObjectUtils.isEmpty(row)) {
            try {
                return this.rowMapper.mapRow(sheet, row, this.currentRow);
            } catch (final Exception e) {
                throw new RuntimeException("当前行数据转换发送了错误." + this.currentRow, e);
            }
        }
        return null;
    }

    private T doReadAllSheet() {
        if (this.noInput) {
            return null;
        }
        final SheetReader sheet = this.getSheet(this.currentSheet);
        String[] headers = sheet.getHeader();
        final String[] row = this.readRow(sheet);
        if (ObjectUtils.isEmpty(row)) {
            this.currentSheet++;
            if (this.currentSheet >= this.getSheets()) {
                LOG.debug("未找到更多的Sheet '" + this.resource.getDescription() + "'.");
                return null;
            } else {
                this.currentRow = 0;
                this.openSheet();
                return this.doReadAllSheet();
            }
        } else {
            try {
                return this.rowMapper.mapRow(sheet, row, this.currentRow);
            } catch (Exception e) {
                throw new RuntimeException("当前行数据转换发送了错误." + this.currentRow, e);
            }
        }
    }

    @Override
    protected void doOpen() throws Exception {
        this.afterPropertiesSet();
        this.noInput = true;
        if (!this.resource.exists()) {
            throw new IllegalStateException("初始化失败,Input resource不存在. " + this.resource.getDescription());
        }
        if (!this.resource.isReadable()) {
            throw new IllegalStateException("初始化失败,Input resource为非可读取. " + this.resource.getDescription());
        }
        this.noInput = false;
        this.openXlsFile(this.resource);
        this.openSheet();
    }

    private String[] readRow(SheetReader sheet) {
        if (this.currentRow < sheet.getRows()) {
            String[] rows = sheet.getRow(this.currentRow);
            ++currentRow;
            return rows;
        }
        return null;
    }

    private void openSheet() {
        final SheetReader sheet = this.getSheet(this.currentSheet);
        LOG.info("打开文件[" + this.resource.getFilename() + "]中的[" + sheet.getSheetName() + "],总共" + sheet.getRows() + "行.");
        for (int i = 0; i < this.linesToSkip; i++) {
            final String[] row = this.readRow(sheet);
            if (this.rowsCallback != null) {
                this.rowsCallback.handleRow(sheet, row);
            }
        }

    }

    @Override
    protected final void doClose() throws Exception {
        LOG.info("执行关闭文件流.....");
        doCloseWorkbook();
        if (getResource() != null) {
            try {
                InputStream is = getResource().getInputStream();
                is.close();
            } catch (IOException ioe) {
                LOG.error("关闭文件输入流发送错误.", ioe);
            }
        }
    }

    protected void doCloseWorkbook() throws Exception {
    }

    protected Resource getResource() {
        return this.resource;
    }

    public void setResource(final Resource resource) {
        this.resource = resource;
    }

    public void setLinesToSkip(final int linesToSkip) {
        this.linesToSkip = linesToSkip;
    }

    protected abstract SheetReader getSheet(int sheet);

    protected abstract int getSheets();

    protected abstract void openXlsFile(Resource resource) throws Exception;

    public void setRowMapper(final RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public void setSkipRowsCallback(final XlsFileSkipRowCallback skipRowsCallback) {
        this.rowsCallback = skipRowsCallback;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.rowMapper, "初始化失败,RowMapper缺失.");
        Assert.notNull(this.resource, "初始化失败,Input resource缺失.");
    }
}
