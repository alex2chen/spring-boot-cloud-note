package com.kxtx.boot.config.partition;

import com.google.common.collect.Maps;
import com.kxtx.boot.batch.JobRequestContext;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.Map;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/10.
 */
public class ImpRangePartitioner implements Partitioner {
    private JobRequestContext requestContext;
    private String inSetting;
    public ImpRangePartitioner(JobRequestContext request,String inSetting) {
        requestContext = request;
        this.inSetting=inSetting;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = Maps.newConcurrentMap();
        ExecutionContext value = new ExecutionContext();
        value.put("importSetting", inSetting);
        result.put("partition1", value);

        //value = new ExecutionContext();
        //value.put("importSetting", "cde");
        result.put("partition2", value);
        return result;
    }
}
