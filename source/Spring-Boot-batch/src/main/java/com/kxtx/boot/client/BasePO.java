package com.kxtx.boot.client;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public class BasePO implements Serializable {
    private boolean isRetry = false;

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }
}
