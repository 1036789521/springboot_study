package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回相应参数实体类
 *
 */
@Data
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    private Boolean status;
    private T result;
    private String message;
    private String code;
}
