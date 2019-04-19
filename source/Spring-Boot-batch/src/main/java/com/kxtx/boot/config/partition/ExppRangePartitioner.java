package com.kxtx.boot.config.partition;

import com.kxtx.boot.batch.JobRequestContext;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 2018/12/10.
 */
public class ExppRangePartitioner implements Partitioner {
    private JobRequestContext requestContext;
    private JdbcOperations jdbcTemplate;

    public ExppRangePartitioner(JobRequestContext request, DataSource dataSource) {
        requestContext = request;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = jdbcTemplate.queryForObject("SELECT MIN(empid) from employee", Integer.class);
        int max = jdbcTemplate.queryForObject("SELECT MAX(empid) from employee", Integer.class);
        int targetSize = (max - min) / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
