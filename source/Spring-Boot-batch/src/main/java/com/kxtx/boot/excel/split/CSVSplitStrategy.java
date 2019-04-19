package com.kxtx.boot.excel.split;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/11/30.
 */
public enum CSVSplitStrategy {
    /**
     * 不拆分
     */
    no,
    /**
     * 平均，比如：5个并发
     */
    average,
    /**
     * 常量级别，每1w触发拆分
     */
    constant
}