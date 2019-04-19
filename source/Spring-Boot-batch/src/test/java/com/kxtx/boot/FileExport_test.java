package com.kxtx.boot;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.model.EmployeeSearch;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileExport_test implements BatchConstant {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileExport_test.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier(EXPORT_CSV_NAME)
    private Job exportJob;
    @Autowired
    @Qualifier(EXPORT_XLS_NAME)
    private Job exportJobXls;

    private String requestVOJson;

    @Before
    public void setUp() {

        JobRequestContext.RequestVO requestVO = new JobRequestContext.RequestVO();
        String params = JSON.toJSONString(new EmployeeSearch() {{
            setId(2);
        }});
        requestVO.setParams(params);
        requestVO.setParamsType(EmployeeSearch.class);
        requestVOJson = JSON.toJSONString(requestVO);
    }

    private String defineSettingJson(String fileSuffix) {
        String exportSettingJson;
        JobRequestContext.ExportSetting exportSetting = new JobRequestContext.ExportSetting();
        exportSetting.setPageSize(3);
        exportSetting.setItemCount(20);
        String filePreffix = "file:target/outputFile-" + DateFormatUtils.format(new Date(),"yyyyMMddHHmmss");
        exportSetting.setOutFilePath(filePreffix + fileSuffix);
        exportSetting.setAndSplitOutAlias("id,name,email");
        exportSetting.setAndSplitOutCols("ID,年龄,邮箱");
        exportSetting.setDelegateBean("employeeService");
        exportSettingJson = JSON.toJSONString(exportSetting);
        return exportSettingJson;
    }

    @Test
    public void go_exportCsv() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info("开始执行export.");
        JobExecution result = jobLauncher.run(exportJob, new JobParametersBuilder()
                .addString(PARAM_SEQ, new Date().toLocaleString())
                .addString(PARAM_OUT, defineSettingJson(".csv"))
                .addString(PARAM_REQUESTVO, requestVOJson)
                .toJobParameters());
        LOGGER.info("执行结果export:{},times:{}", result.getStatus(), stopwatch);
    }

    @Test
    public void go_exportXls() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info("开始执行export.");
        JobExecution result = jobLauncher.run(exportJobXls, new JobParametersBuilder()
                .addString(PARAM_SEQ, new Date().toLocaleString())
                .addString(PARAM_OUT, defineSettingJson(".xlsx"))
                .addString(PARAM_REQUESTVO, requestVOJson)
                .toJobParameters());
        LOGGER.info("执行结果export:{},times:{}", result.getStatus(), stopwatch);
    }
}
