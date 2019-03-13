package com.example.demo.util.excel;


/**
 * Excel数据加载回调
 */
public abstract class ExcelExportRecordLoadCallback implements ExcelExportRecordLoadCallbackInteface {


    @Override
    public int[] getCellWidths() {
        return new int[getCellTitles().length];
    }

    @Override
    public CellAlignType[] getCellAlignTypes() {
        CellAlignType[] cellAlignTypes = new CellAlignType[getCellTitles().length];
        for (int i = 0; i < cellAlignTypes.length; i++) {
            // 默认左对齐
            cellAlignTypes[i] = CellAlignType.LEFT;
        }
        return cellAlignTypes;
    }
}
