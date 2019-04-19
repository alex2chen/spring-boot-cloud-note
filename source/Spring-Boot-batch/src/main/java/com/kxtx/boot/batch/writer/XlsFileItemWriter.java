package com.kxtx.boot.batch.writer;

import com.kxtx.boot.client.BaseVO;
import com.kxtx.boot.client.InServiceAgent;
import com.kxtx.boot.excel.SheetWriter;
import com.kxtx.boot.excel.WorkbookProxy;
import com.kxtx.boot.excel.support.PoiSheetWriter;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.batch.listen.XlsFileFooterCallback;
import com.kxtx.boot.batch.listen.XlsFileHeaderCallback;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.WriteFailedException;
import org.springframework.batch.item.WriterNotOpenException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/27
 */
public class XlsFileItemWriter<T> extends AbstractItemStreamItemWriter<T> implements ResourceAwareItemWriterItemStream<T>, CustomItemWriter<T>, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(XlsFileItemWriter.class);
    private Resource resource;
    private FieldExtractor<T> fieldExtractor;
    private String encoding = "UTF-8";
    private XlsFileHeaderCallback headerCallback;
    private XlsFileFooterCallback footerCallback;
    private boolean saveState = true;//是否保存写入状态
    private boolean forceSync = false;//是否强制同步写入
    private boolean shouldDeleteIfExists = true;//文件已经存在是否先删除
    private boolean shouldDeleteIfEmpty = false;//没有记录写入文件的情况下是否删除
    private boolean transactional;
    private boolean append;
    private XlsFileItemWriter<T>.OutputState state;

    public XlsFileItemWriter() {
        this.transactional = true;
        this.append = false;
        this.setExecutionContextName(ClassUtils.getShortName(XlsFileItemWriter.class));
    }

    public FieldExtractor<BaseVO> createFieldExtractor(String[] colNames) {
        BeanWrapperFieldExtractor<BaseVO> fieldExtractor = new BeanWrapperFieldExtractor<BaseVO>();
        fieldExtractor.setNames(colNames);
        return fieldExtractor;
    }

    @Override
    public void setServiceAgent(InServiceAgent<BaseVO, ? extends BasePO> inServiceAgent) {
        throw new RuntimeException("非法执行路径.");
    }

    private XlsFileItemWriter<T>.OutputState getOutputState() {
        if (this.state == null) {
            File file;
            try {
                file = this.resource.getFile();
            } catch (IOException var3) {
                throw new ItemStreamException("加载文件文件失败,[" + this.resource + "]", var3);
            }
            Assert.state(!file.exists() || file.canWrite(), "文件写入失败,文件可能不存在或无权限写入: [" + this.resource + "]");
            this.state = new XlsFileItemWriter.OutputState();
            this.state.setDeleteIfExists(this.shouldDeleteIfExists);
            this.state.setAppendAllowed(this.append);
            this.state.setEncoding(this.encoding);
        }
        return this.state;
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        if (!this.getOutputState().isInitialized()) {
            throw new WriterNotOpenException("初始化失败,OutputState参数有误.");
        } else {
            LOGGER.info("开始写入" + items.size() + " 行数据.");
            XlsFileItemWriter.OutputState state = this.getOutputState();
            try {
                items.forEach((item) -> {
                    try {
                        state.write(fieldExtractor.extract(item));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ++state.linesWritten;
                });
                state.writer.flushRows(state.sheet, items.size());
            } catch (IOException var7) {
                throw new WriteFailedException("Could not write data.  The file may be corrupt.", var7);
            }
        }
    }

    public void close() {
        super.close();
        if (this.state != null) {
            try {
                if (this.footerCallback != null && this.state.writer != null) {
                    this.footerCallback.writeFooter(this.state.writer, this.state.sheet);
                    this.state.writer.flushRows(this.state.sheet);
                }
                this.state.writer.getDelegate().write(this.state.fileOut);
            } catch (IOException var9) {
                throw new ItemStreamException("Failed to write footer before closing", var9);
            } finally {
                this.state.close();
                if (this.state.linesWritten == 0L && this.shouldDeleteIfEmpty) {
                    try {
                        this.resource.getFile().delete();
                    } catch (IOException var8) {
                        throw new ItemStreamException("Failed to delete empty file on close", var8);
                    }
                }
                this.state = null;
            }
        }

    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.open(executionContext);
        Assert.notNull(this.resource, "The resource must be set");
        if (!this.getOutputState().isInitialized()) {
            this.doOpen(executionContext);
        }

    }

    private void doOpen(ExecutionContext executionContext) throws ItemStreamException {
        XlsFileItemWriter.OutputState outputState = this.getOutputState();
        if (executionContext.containsKey(this.getExecutionContextKey("current.count"))) {
            outputState.restoreFrom(executionContext);
        }
        try {
            outputState.initializeWriter();
        } catch (IOException var5) {
            throw new ItemStreamException("Failed to initialize writer", var5);
        }
        if (outputState.lastMarkedByteOffsetPosition == 0L && !outputState.appending && this.headerCallback != null) {
            try {
                this.headerCallback.writeHeader(outputState.writer, outputState.sheet);
            } catch (IOException var4) {
                throw new ItemStreamException("Could not write headers.  The file may be corrupt.", var4);
            }
        }
    }

    public void update(ExecutionContext executionContext) {
        super.update(executionContext);
        if (this.state == null) {
            throw new ItemStreamException("ItemStream not open or already closed.");
        } else {
            Assert.notNull(executionContext, "ExecutionContext must not be null");
            if (this.saveState) {
                try {
                    executionContext.putLong(this.getExecutionContextKey("current.count"), this.state.position());
                } catch (IOException var3) {
                    throw new ItemStreamException("ItemStream does not return current position properly", var3);
                }
                executionContext.putLong(this.getExecutionContextKey("written"), this.state.linesWritten);
            }

        }
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.fieldExtractor, "初始化失败,fieldExtractor缺失.");
        if (this.append) {
            this.shouldDeleteIfExists = false;
        }
    }

    public FieldExtractor<T> getFieldExtractor() {
        return fieldExtractor;
    }

    public void setFieldExtractor(FieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    public void setHeaderCallback(XlsFileHeaderCallback headerCallback) {
        this.headerCallback = headerCallback;
    }

    public void setFooterCallback(XlsFileFooterCallback footerCallback) {
        this.footerCallback = footerCallback;
    }

    public void setSaveState(boolean saveState) {
        this.saveState = saveState;
    }

    public void setForceSync(boolean forceSync) {
        this.forceSync = forceSync;
    }

    public void setShouldDeleteIfExists(boolean shouldDeleteIfExists) {
        this.shouldDeleteIfExists = shouldDeleteIfExists;
    }

    public void setShouldDeleteIfEmpty(boolean shouldDeleteIfEmpty) {
        this.shouldDeleteIfEmpty = shouldDeleteIfEmpty;
    }


    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    public void setEncoding(String newEncoding) {
        this.encoding = newEncoding;
    }

    public void setAppendAllowed(boolean append) {
        this.append = append;
        this.shouldDeleteIfExists = false;
    }

    private class OutputState {
        private static final String DEFUALT_SHEETNAME = "data";
        SheetWriter writer;
        Sheet sheet;
        private FileOutputStream fileOut;
        String encoding;
        boolean restarted;
        long lastMarkedByteOffsetPosition;
        long linesWritten;
        boolean shouldDeleteIfExists;
        boolean initialized;
        private boolean append;
        private boolean appending;

        private OutputState() {
            this.encoding = "UTF-8";
            this.restarted = false;
            this.lastMarkedByteOffsetPosition = 0L;
            this.linesWritten = 0L;
            this.shouldDeleteIfExists = true;
            this.initialized = false;
            this.append = false;
            this.appending = false;
        }

        public long position() throws IOException {
            long pos = 0L;
            if (this.fileOut == null) {
                return 0L;
            } else {
                pos = this.sheet.getLastRowNum();
                return pos;
            }
        }

        public void setAppendAllowed(boolean append) {
            this.append = append;
        }

        public void restoreFrom(ExecutionContext executionContext) {
            this.lastMarkedByteOffsetPosition = executionContext.getLong(XlsFileItemWriter.this.getExecutionContextKey("current.count"));
            this.linesWritten = executionContext.getLong(XlsFileItemWriter.this.getExecutionContextKey("written"));
            if (XlsFileItemWriter.this.shouldDeleteIfEmpty && this.linesWritten == 0L) {
                this.restarted = false;
                this.lastMarkedByteOffsetPosition = 0L;
            } else {
                this.restarted = true;
            }

        }

        public void setDeleteIfExists(boolean shouldDeleteIfExists) {
            this.shouldDeleteIfExists = shouldDeleteIfExists;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public void close() {
            this.initialized = false;
            this.restarted = false;
            try {
                if (this.fileOut != null) {
                    this.fileOut.close();
                }
                if (this.writer != null) {
                    this.writer.getDelegate().close();
                }
            } catch (IOException var5) {
                throw new ItemStreamException("Unable to close the the ItemWriter", var5);
            }
        }

        public void write(Object... fields) throws IOException {
            if (!this.initialized) {
                this.initializeWriter();
            }
            if (fields != null && fields.length > 0) {
                this.writer.addRow(sheet, fields);
            }
        }

        private void initializeWriter() throws IOException {
            File file = XlsFileItemWriter.this.resource.getFile();
            FileUtils.setUpOutputFile(file, this.restarted, this.append, this.shouldDeleteIfExists);
            this.fileOut = new FileOutputStream(file.getAbsolutePath(), true);
            writer = new PoiSheetWriter(WorkbookProxy.createWorkbook(true, true));
            sheet = writer.createSheet(DEFUALT_SHEETNAME);
            if (this.append && file.length() > 0L) {
                this.appending = true;
            }
            Assert.state(this.writer != null);
            if (this.restarted) {
                this.checkFileSize();
            }
            this.initialized = true;
        }

        public boolean isInitialized() {
            return this.initialized;
        }

        private void checkFileSize() throws IOException {
            long size = -1L;
            this.writer.flushRows(sheet);
            size = this.sheet.getLastRowNum();
            if (size < this.lastMarkedByteOffsetPosition) {
                throw new ItemStreamException("Current file size is smaller than size at last commit");
            }
        }
    }
}
