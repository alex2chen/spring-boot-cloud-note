package com.kxtx.boot.batch;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
@Deprecated
public class JobRequest implements Serializable {
    //是否同步
    private boolean sync = true;

    private String filepath;

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
