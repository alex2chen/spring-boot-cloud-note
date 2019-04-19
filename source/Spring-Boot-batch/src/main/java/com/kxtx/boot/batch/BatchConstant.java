package com.kxtx.boot.batch;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public interface BatchConstant {
    //    final String PARAM_REQUEST = "param";
//    final String PARAM_REQUEST_SCOPE = "#{jobParameters['param']}";
    final String PARAM_SEQ = "sequenceKey";

    final String PARAM_IN = "importSetting";
    final String PARAM_IN_SCOPE = "#{jobParameters['importSetting']}";

    final String PARAM_OUT = "exportSetting";
    final String PARAM_OUT_SCOPE = "#{jobParameters['exportSetting']}";

    final String PARAM_REQUESTVO = "requestVO";
    final String PARAM_REQUESTVO_SCOPE = "#{jobParameters['requestVO']}";

    final String IMPORT_CSV_NAME = "importCsvJob";
    final String IMPORT_PARTITION_NAME = "importPartitionJob";
    final String IMPORT_XLS_NAME = "importXlsJob";
    final String EXPORT_CSV_NAME = "exportCsvJob";
    final String EXPORT_XLS_NAME = "exportXlsJob";
}
