package com.example.demo.exception;


/**
 * The type Business exception.
 * 自定义异常
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 3455708526465670030L;
    private String  code;
    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String code, String msg){
        super(msg);
        this.code=code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}