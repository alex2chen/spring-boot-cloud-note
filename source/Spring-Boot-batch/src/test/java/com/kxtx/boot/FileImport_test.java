package com.kxtx.boot;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.kxtx.boot.batch.BatchConstant;
import com.kxtx.boot.batch.JobRequestContext;
import com.kxtx.boot.config.partition.SplitMessage;
import com.kxtx.boot.config.partition.StepExecutionRequestHandler;
import com.kxtx.boot.model.Employee;
import com.kxtx.boot.model.EmployeeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
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
 * @date 2017/9/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileImport_test implements BatchConstant {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileImport_test.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier(IMPORT_CSV_NAME)
    private Job importJob;
    @Autowired
    @Qualifier(IMPORT_XLS_NAME)
    private Job importXlsJob;

    @Autowired
    @Qualifier(IMPORT_PARTITION_NAME)
    private Job partitionJob;

    @Test
    public void go_importCsv() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info("开始执行import.");
        JobExecution result = jobLauncher.run(importJob, new JobParametersBuilder()
                .addString(PARAM_SEQ, new Date().toLocaleString())
                .addString(PARAM_IN, defineSettingJson("demo.csv"))
                .toJobParameters());
        LOGGER.info("执行结果import:{},times:{}", result.getStatus(), stopwatch);
    }

    @Test
    public void go_partitionMstJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info("开始执行import.");
        JobExecution result = jobLauncher.run(partitionJob, new JobParametersBuilder()
                .addString(PARAM_SEQ, new Date().toLocaleString())
                .addString(PARAM_IN, defineSettingJson("demo.csv"))
                .toJobParameters());
        LOGGER.info("执行结果import:{},times:{}", result.getStatus(), stopwatch);
    }

    @Autowired
    private StepExecutionRequestHandler stepExecutionRequestHandler;

    @Test
    public void go_partitionSlvJob() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int seq = 0;
        int size = 2;
        long stepExecutionId = 36;
        long jobExecutionId = 19;
        SplitMessage splitMessage = new SplitMessage(seq, size, jobExecutionId, stepExecutionId, "impSlvPartitionStep");
        StepExecution result = stepExecutionRequestHandler.handle(splitMessage);
        LOGGER.info("执行结果import:{},times:{}", result.getSummary(), stopwatch);
    }

    @Test
    public void go_importXls() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info("开始执行import.");
        //TODO::校验文件格式
        JobExecution result = jobLauncher.run(importXlsJob, new JobParametersBuilder()
                .addString(PARAM_SEQ, new Date().toLocaleString())
                .addString(PARAM_IN, defineSettingJson("demo.xlsx"))
                .toJobParameters());
        LOGGER.info("执行结果import:{},times:{}", result.getStatus(), stopwatch);
    }

    private String defineSettingJson(String inputFile) {
        JobRequestContext.ImportSetting importSetting = new JobRequestContext.ImportSetting();
        importSetting.setInputFilePath(inputFile);
        importSetting.setAndSplitInputCols("姓名,邮箱,性别");
        importSetting.setAndSplitInputAlias("name,email,gender");
        importSetting.setSourceType(EmployeeVO.class);
        importSetting.setTargetType(Employee.class);
        importSetting.setCopyProperty(true);
        importSetting.setDelegateBean("employeeService");
        return JSON.toJSONString(importSetting);
    }

    /**
     * 多线程问题
     * https://stackoverflow.com/questions/24558703/multi-threaded-acces-to-job-scope-beans-in-spring-batch-3-0
     */
    @Test
    public void go_concurrentRun() {
        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                try {

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }
}
