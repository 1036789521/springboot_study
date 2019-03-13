package com.example.demo.util.excel;

import java.util.List;

/**
 * Excel导入行回调
 */
public interface ExcelImportRowCallback {

    /**
     * 行数据校验、保存
     * @author yangqh
     * @date 2019/2/26
     **/
    ExcelImportRowResult checkAndSave(int row, List<Object> cellValues);

}
