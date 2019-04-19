package com.kxtx.boot.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/28
 */
public class WorkbookProxy {
    public static Workbook createWorkbook(Resource resource) throws IOException, InvalidFormatException {
        Assert.notNull(resource, "init Workbook occured error,resource is required.");
        return WorkbookFactory.create(resource.getInputStream());
    }

    public static Workbook createWorkbook(boolean xlsx, boolean useFlush) {
        //return xlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        SXSSFWorkbook wb = new SXSSFWorkbook(-1);
        wb.setCompressTempFiles(true);//启用压缩
        return xlsx && useFlush ? wb : new XSSFWorkbook();
    }
}
