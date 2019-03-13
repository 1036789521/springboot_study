package com.example.demo.util.excel;

/**
 * Excel导入校验异常类
 */
public class ExcelImportVerifyException extends Exception {

    private ExcelImportRowResult excelImportRowResult;

    public ExcelImportVerifyException() {
        super();
    }

    public ExcelImportVerifyException(String message) {
        super(message);
    }

    public ExcelImportVerifyException(ExcelImportRowResult excelImportRowResult) {
        super(excelImportRowResult.getMessage());
        this.excelImportRowResult = excelImportRowResult;
    }

    public ExcelImportVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelImportVerifyException(Throwable cause) {
        super(cause);
    }

    public ExcelImportRowResult getExcelImportRowResult() {
        return excelImportRowResult;
    }
}
