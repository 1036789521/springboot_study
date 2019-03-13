package com.example.demo.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 */
public class ExcelUtils {

    /**
     * Excel导出
     *
     * @param out                输出流
     * @param recordLoadCallback 数据加载回调
     * @throws IOException
     */
    public static void exportExcel(OutputStream out, ExcelExportRecordLoadCallback recordLoadCallback) throws IOException {

        String[] cellTitles = recordLoadCallback.getCellTitles();
        int[] cellWidths = recordLoadCallback.getCellWidths();
        CellAlignType[] cellAlignTypes = recordLoadCallback.getCellAlignTypes();

        if (cellTitles == null) {
            throw new ExcelExportVerifyException("标题栏数组不能为空");
        }
        if (cellWidths == null) {
            throw new ExcelExportVerifyException("单元格宽度数组不能为空");
        }
        if (cellAlignTypes == null) {
            throw new ExcelExportVerifyException("单元格内容对齐类型数组不能为空");
        }
        if (cellTitles.length != cellWidths.length || cellTitles.length != cellAlignTypes.length) {
            throw new ExcelExportVerifyException("cellTitles、cellWidths、cellAlignTypes三者长度不一致");
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sh = workbook.createSheet();


        int rowNum = 0;

        // 单元格样式map，只需要在生成标题单元格时创建一次，后续的数据单元格可共用
        Map<Integer, XSSFCellStyle> cellStyles = new HashMap<>();

        // 创建单元格标题行（即第一行）
        XSSFRow titleRow = sh.createRow(rowNum);
        for (int i = 0; i < cellTitles.length; i++) {

            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(cellAlignTypes[i].getAlignment());

            cellStyles.put(i, cellStyle);

            createCell(titleRow, i, cellTitles[i], cellStyle);

            if (cellWidths[i] > 0) {
                sh.setColumnWidth(i, cellWidths[i] * 256);
            }

        }
        rowNum++;

        // 判断数据是否加载完成
        while (!recordLoadCallback.isFinished()) {
            // 加载数据
            List<String[]> records = recordLoadCallback.loadRecords();
            for (int i = 0; i < records.size(); i++, rowNum++) {

                // 创建数据行
                XSSFRow row = sh.createRow(rowNum);
                String[] cellValues = records.get(i);
                // 填充单元格
                for (int cellNum = 0; cellNum < cellTitles.length; cellNum++) {

                    createCell(row, cellNum, cellValues[cellNum], cellStyles.get(cellNum));
                }
            }
        }

        try {
            workbook.write(out);
        } finally {
            try {
                // dispose of temporary files backing this workbook on disk
                //workbook.dispose();
            } finally {
                workbook.close();
            }
        }
    }

    /**
     * 创建单元格
     *
     * @param row       行实例
     * @param cellNum   单元格序号
     * @param value     单元格填充值
     * @param cellStyle 单元格样式
     */
    private static void createCell(XSSFRow row, int cellNum, String value, XSSFCellStyle cellStyle) {
        XSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(value);

        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * Excel导入
     *
     * @param file
     * @param rowCallback
     * @return
     * @throws IOException
     * @throws ExcelImportVerifyException
     */
    public static List<ExcelImportRowResult> importExcel(MultipartFile file, ExcelImportRowCallback rowCallback) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new ExcelExportVerifyException("上传文件格式不正确(*.xls/*.xlsx)");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is;
        Workbook wb;
        try {
            is = file.getInputStream();
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("流异常");
        }
        int numberOfSheets = wb.getNumberOfSheets();
        List<ExcelImportRowResult> rowResultList = new ArrayList<>();
        for (int indexOfSheet = 0; indexOfSheet < numberOfSheets; indexOfSheet++) {
            Sheet sheet = wb.getSheetAt(indexOfSheet);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            // 第一行（标题栏）忽略
            for (int row = 1; row < physicalNumberOfRows; row++) {
                Row sheetRow = sheet.getRow(row);
                List<Object> cellValues = new ArrayList<>();
                for (int i = 0; i < sheetRow.getLastCellNum(); i++) {
                    Object cellValue = getCellValue(sheetRow.getCell(i));
                    cellValues.add(cellValue);
                }
                ExcelImportRowResult rowResult = rowCallback.checkAndSave(row, cellValues);
                if (rowResult.isBlockedErr()) {
                    throw new ExcelExportVerifyException(rowResult);
                }
                if (rowResult.isErr()) {
                    rowResultList.add(rowResult);
                }
            }
        }
        try {
            is.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowResultList;
    }

    /**
     * @Description: 获取表格单元格值
     **/
    public static String getCellValue(Cell cell) {
        String cellValue;
        switch (cell.getCellType()) {
            // 数字
            case HSSFCell.CELL_TYPE_NUMERIC:
                cellValue = cell.getNumericCellValue() + "";
                break;
            // 字符串
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            // Boolean
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            // 公式
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            // 空值
            case HSSFCell.CELL_TYPE_BLANK:
                cellValue = "";
                break;

            default:
                cellValue = cell.getStringCellValue();
                break;
        }
        return cellValue;
    }
}
