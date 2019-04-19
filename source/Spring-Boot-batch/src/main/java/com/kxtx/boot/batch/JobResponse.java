package com.kxtx.boot.batch;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
@Deprecated
public class JobResponse implements Serializable {
    //是否成功
    private boolean suc=false;

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }
}
