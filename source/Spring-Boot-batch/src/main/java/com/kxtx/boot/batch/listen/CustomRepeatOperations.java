package com.kxtx.boot.batch.listen;

import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatException;
import org.springframework.batch.repeat.RepeatOperations;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by YT on 2018/1/11.
 */
public class CustomRepeatOperations implements RepeatOperations {
    @Override
    public RepeatStatus iterate(RepeatCallback repeatCallback) throws RepeatException {
        return RepeatStatus.FINISHED;
    }
}
