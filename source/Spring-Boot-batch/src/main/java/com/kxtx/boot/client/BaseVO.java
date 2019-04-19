package com.kxtx.boot.client;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public class BaseVO implements Serializable {
    private int rowId;
    private int requestId;
    private String resultMsg;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public void formatResultMsg(String resultMsg) {
        this.resultMsg = String.format("第%s行数据，%s", rowId, resultMsg);
    }
}
