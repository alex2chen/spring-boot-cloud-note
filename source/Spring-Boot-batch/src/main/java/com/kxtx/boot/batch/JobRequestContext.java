package com.kxtx.boot.batch;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.kxtx.boot.client.BasePO;
import com.kxtx.boot.client.BaseSearch;
import com.kxtx.boot.client.BaseVO;
import org.springframework.util.ClassUtils;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/20
 */
public class JobRequestContext implements Serializable {
    //通用设置
    private int chunkSize = 3;//3;
    private ImportSetting in;
    private ExportSetting out;
    //延迟2次序列化消耗
    private String requestVO;

    public void setIn(ImportSetting in) {
        this.in = in;
    }

    public void setOut(ExportSetting out) {
        this.out = out;
    }

    public ImportSetting getIn() {
        return in;
    }

    public ExportSetting getOut() {
        return out;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getRequestVO() {
        return requestVO;
    }

    public void setRequestVO(String requestVO) {
        this.requestVO = requestVO;
    }

    public static class ImportSetting {
        private String delegateBean;
        private String inputFilePath;
        private String[] inputCols;
        private String[] inputAlias;
        private Class<? extends BaseVO> sourceType;
        private boolean copyProperty = true;
        private Class<? extends BasePO> targetType;

        public String getDelegateBean() {
            return delegateBean;
        }

        public void setDelegateBean(String delegateBean) {
            this.delegateBean = delegateBean;
        }

        public String getInputFilePath() {
            return inputFilePath;
        }

        public void setInputFilePath(String inputFilePath) {
            this.inputFilePath = inputFilePath;
        }

        public String[] getInputCols() {
            return inputCols;
        }

        public void setInputCols(String[] inputCols) {
            this.inputCols = inputCols;
        }

        public String[] getInputAlias() {
            return inputAlias;
        }

        public void setInputAlias(String[] inputAlias) {
            this.inputAlias = inputAlias;
        }

        public Class<? extends BaseVO> getSourceType() {
            return sourceType;
        }

        public void setSourceType(Class<? extends BaseVO> sourceType) {
            this.sourceType = sourceType;
        }

        public boolean isCopyProperty() {
            return copyProperty;
        }

        public void setCopyProperty(boolean copyProperty) {
            this.copyProperty = copyProperty;
        }

        public Class<? extends BasePO> getTargetType() {
            return targetType;
        }

        public void setTargetType(Class<? extends BasePO> targetType) {
            this.targetType = targetType;
        }

        public void setAndSplitInputCols(String inputCols) {
            if (!Strings.isNullOrEmpty(inputCols)) {
                this.inputCols = Splitter.on(",").omitEmptyStrings().splitToList(inputCols).toArray(new String[]{});
            }
        }

        public void setAndSplitInputAlias(String inputAlias) {
            if (!Strings.isNullOrEmpty(inputAlias)) {
                this.inputAlias = Splitter.on(",").omitEmptyStrings().splitToList(inputAlias).toArray(new String[]{});
            }
        }

        public void setAndParseSourceType(String sourceType) {
            if (!Strings.isNullOrEmpty(sourceType)) {
                Class<?> sourceClz = null;
                try {
                    sourceClz = ClassUtils.forName(sourceType, this.getClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("启动参数配置有误,请检查sourceType," + e.getMessage());
                }
                if (ClassUtils.isAssignable(BaseVO.class, sourceClz.getSuperclass())) {
                    this.sourceType = (Class<? extends BaseVO>) sourceClz;
                }
            }
        }

        public void setAndParseTargetType(String targetType) {
            if (!Strings.isNullOrEmpty(targetType)) {
                Class<?> targetClz = null;
                try {
                    targetClz = ClassUtils.forName(targetType, this.getClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("启动参数配置有误,请检查targetType," + e.getMessage());
                }
                if (ClassUtils.isAssignable(BasePO.class, targetClz.getSuperclass())) {
                    this.targetType = (Class<? extends BasePO>) targetClz;
                }
            }
        }
    }

    public static class ExportSetting {
        private String delegateBean;
        private String outFilePath;
        private String[] outAlias;
        private String[] outCols;
        private int pageSize;
        private int itemCount;

        public String getDelegateBean() {
            return delegateBean;
        }

        public void setDelegateBean(String delegateBean) {
            this.delegateBean = delegateBean;
        }

        public String getOutFilePath() {
            return outFilePath;
        }

        public void setOutFilePath(String outFilePath) {
            this.outFilePath = outFilePath;
        }

        public String[] getOutAlias() {
            return outAlias;
        }

        public void setOutAlias(String[] outAlias) {
            this.outAlias = outAlias;
        }

        public String[] getOutCols() {
            return outCols;
        }

        public void setOutCols(String[] outCols) {
            this.outCols = outCols;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public void setAndSplitOutCols(String outCols) {
            if (!Strings.isNullOrEmpty(outCols)) {
                this.outCols = Splitter.on(",").omitEmptyStrings().splitToList(outCols).toArray(new String[]{});
            }
        }

        public void setAndSplitOutAlias(String outAlias) {
            if (!Strings.isNullOrEmpty(outAlias)) {
                this.outAlias = Splitter.on(",").omitEmptyStrings().splitToList(outAlias).toArray(new String[]{});
            }
        }
    }

    public static class RequestVO {
        private String params;
        private Class<? extends BaseSearch> paramsType;

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public Class<? extends BaseSearch> getParamsType() {
            return paramsType;
        }

        public void setParamsType(Class<? extends BaseSearch> paramsType) {
            this.paramsType = paramsType;
        }
    }

}
