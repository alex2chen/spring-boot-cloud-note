package com.kxtx.boot.excel.split;


import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Map;

/**
 * @Author: alex
 * @Description:
 * @Date: created in 16:07 2018/11/28
 */
public class XLSXSplitCSV {
    private static final Logger LOGGER = LoggerFactory.getLogger(XLSXSplitCSV.class);

    public synchronized void readSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputSource sheetSource, SplitHolder splitHolder) throws IOException, ParserConfigurationException, SAXException {
        XMLReader sheetParser = SAXHelper.newXMLReader();
        DataFormatter formatter = new DataFormatter();
        DefaultXSSFSheetHandler sheetHandler = new DefaultXSSFSheetHandler(splitHolder);
        ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
        sheetParser.setContentHandler(handler);
        splitHolder.initializing();
        sheetParser.parse(sheetSource);
        splitHolder.disposable();
    }

    public SplitResponse read(SplitRequest request) {
        SplitResponse response = new SplitResponse();
        InputStream stream = null;
        try (OPCPackage xlsxPackage = OPCPackage.open(request.getInputFilePath(), PackageAccess.READ)) {
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(xlsxPackage);
            XSSFReader xssfReader = new XSSFReader(xlsxPackage);
            StylesTable styles = xssfReader.getStylesTable();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            int index = 0;
            if (iter.hasNext()) {
                stream = iter.next();
                InputSource sheetSource = new InputSource(stream);
                SplitHolder splitHolder = new SplitHolder(request, response);
                readSheet(styles, strings, sheetSource, splitHolder);
            }
            return response;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    class DefaultXSSFSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        private SplitHolder splitHolder;
        private boolean firstCellOfRow;
        private int maxColumns = -1;
        private int currentRow = -1;
        private int currentCol = -1;
        private Map<Integer, String> line;

        public DefaultXSSFSheetHandler(SplitHolder splitHolder) {
            this.splitHolder = splitHolder;
            line = Maps.newHashMap();
        }

        @Override
        public void startRow(int rowNum) {
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
            line.clear();
        }

        @Override
        public void endRow(int rowNum) {
            if (line.isEmpty()) {
                splitHolder.println();
                return;
            }
            Object[] cols = new String[maxColumns + 1];
            line.entrySet().forEach(x -> cols[x.getKey()] = x.getValue());
            splitHolder.println(cols);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment xssfComment) {
            if (firstCellOfRow) {
                firstCellOfRow = false;
            } else {
                splitHolder.print(",");
            }
            //优雅地处理缺失和XSSFCell CellRef在类似的方式
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }
            int colIndex = nameToColumn(cellReference);
            currentCol = colIndex;
            if (currentRow == 0) {
                maxColumns = colIndex;
            }
            if (!Strings.isNullOrEmpty(formattedValue)) {
                //csv禁用
                formattedValue = formattedValue.replace(",", "");
            }
            line.put(colIndex, formattedValue);
        }

        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                if (Character.isDigit(c)) {
                    continue;
                }
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }


}
