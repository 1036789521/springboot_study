package com.example.demo.util.excel;

import java.util.List;

/**
 * Excel行数据校验结果信息
 *
 */
public class ExcelImportRowResult {

    private int rowNum;

    private List<Object> cellValues;

    private String message;

    private boolean err;

    private boolean blockedErr;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public List<Object> getCellValues() {
        return cellValues;
    }

    public void setCellValues(List<Object> cellValues) {
        this.cellValues = cellValues;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public boolean isBlockedErr() {
        return blockedErr;
    }

    public void setBlockedErr(boolean blockedErr) {
        this.blockedErr = blockedErr;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExcelImportRowResult{");
        sb.append("rowNum=").append(rowNum);
        sb.append(", cellValues=").append(cellValues);
        sb.append(", message='").append(message).append('\'');
        sb.append(", err=").append(err);
        sb.append(", blockedErr=").append(blockedErr);
        sb.append('}');
        return sb.toString();
    }
}
