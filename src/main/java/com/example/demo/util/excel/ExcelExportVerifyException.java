package com.example.demo.util.excel;

/**
 * Excel导出校验异常类
 */
public class ExcelExportVerifyException extends RuntimeException {

    public ExcelExportVerifyException() {
        super();
    }

    public ExcelExportVerifyException(String message) {
        super(message);
    }

    public ExcelExportVerifyException(ExcelImportRowResult excelImportRowResult) {
        super(excelImportRowResult.getMessage());
    }

    public ExcelExportVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelExportVerifyException(Throwable cause) {
        super(cause);
    }
}