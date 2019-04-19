package com.kxtx.boot.excel;

/**基于usermodel
 * Created by YT on 2017/9/25.
 */
public interface SheetReader {
    String getSheetName();
    String[] getHeader();
    int getRows();
    int getColumns();
    String[] getRow(int rowIndex);

}
