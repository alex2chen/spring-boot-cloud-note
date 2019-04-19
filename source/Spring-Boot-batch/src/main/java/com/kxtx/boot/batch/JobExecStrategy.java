package com.kxtx.boot.batch;

/**
 * 由于spring-batch的prameter采用了后绑定技术，scope属性为step就是在生成Step的时候，才去创建bean，因为这个时候jobparameter才传过来。
 * 另外，就是spring-batch大量使用泛型，严重约定了类型
 * Created by YT on 2017/9/19.
 */
@Deprecated
public enum JobExecStrategy {
    Dynamic,
    Static
}
