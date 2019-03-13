package com.example.demo.util.excel;

import java.util.List;

/**
 * Excel数据加载回调
 * 用于加载指定的数据，判断数据是否已加载完毕。实现大数据加载
 */
public interface ExcelExportRecordLoadCallbackInteface {

    /**
     * 导出Excel时，会先通过getCellTitles()填充单元格标题（即第一行数据）；
     * 继而循环通过isFinished()判断数据是否加载完成，否则继续调用loadRecords()生成Excel行数据。
     *
     */

    /**
     * 单元格标题
     *
     * @return
     */
    String[] getCellTitles();

    /**
     * 单元格宽度（单位：一个字节长度）
     *
     * @return
     */
    int[] getCellWidths();

    /**
     * 单元格内容对齐类型
     *
     * @return
     */
    CellAlignType[] getCellAlignTypes();

    /**
     * 数据加载
     *
     * @return
     */
    List<String[]> loadRecords();

    /**
     * 是否加载完成
     *
     * @return
     */
    boolean isFinished();

    /**
     * 保留在内存中的行数，其它数据将缓存到磁盘，保证堆不轻易溢出。
     * 分页加载时，建议与每页数据量大小保持一致
     *
     * @return
     */
    int getRowAccessWindowSize();
}
