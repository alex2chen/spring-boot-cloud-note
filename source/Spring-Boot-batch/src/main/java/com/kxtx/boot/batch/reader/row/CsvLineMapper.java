package com.kxtx.boot.batch.reader.row;

import com.kxtx.boot.client.BaseVO;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

/**
 * 实现行号
 * Created by YT on 2017/10/23.
 */
public class CsvLineMapper extends DefaultLineMapper<BaseVO> {
    @Override
    public BaseVO mapLine(String line, int lineNumber) throws Exception {
        BaseVO baseVO = super.mapLine(line, lineNumber);
        baseVO.setRowId(lineNumber);
        return baseVO;
    }
}
