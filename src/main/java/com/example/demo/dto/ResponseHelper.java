package com.example.demo.dto;

import cn.hutool.core.util.ObjectUtil;
import com.example.demo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * The type Response helper.
 * 统一返回相应参数
 */
@Slf4j
public class ResponseHelper {

    /**
     * Instantiates a new Response helper.
     * 无参构造方法
     */
    public ResponseHelper() {
    }

    /**
     * 500错误 - 无参
     */
    public static <T> ResponseModel<T> failServerError() {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setMessage(ResultConstant.FAILED);
        return response;
    }

    /**
     * 500错误 - 有参
     */
    public static <T> ResponseModel<T> failServerError(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setMessage(message);
        return response;
    }

    /** 
    * @Description: 带自定义编码的异常错误
    */ 
    public static <T> ResponseModel<T> failServerError(String code,String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(StringUtils.isNotEmpty(code)?code:String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setMessage(message);
        return response;
    }


    /**
     * 错误的请求
     */
    public static <T> ResponseModel<T> failBadReq() {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setMessage(ResultConstant.FAILED);
        return response;
    }

    /**
     * 错误的请求
     */
    public static <T> ResponseModel<T> failBadReq(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        response.setMessage(message);
        return response;
    }

    /**
     * 未授权错误
     */
    public static <T> ResponseModel<T> failUnauthorized() {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        response.setMessage(ResultConstant.FAILED);
        return response;
    }

    /**
     * 未授权错误
     */
    public static <T> ResponseModel<T> failUnauthorized(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.FALSE);
        response.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        response.setMessage(message);
        return response;
    }

    /**
     * 成功返回
     *
     */
    public static <T> ResponseModel<T> success(T result) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.TRUE);
        response.setCode(String.valueOf(HttpStatus.OK));
        response.setMessage(ResultConstant.SUCCEED);
        response.setResult(result);
        return response;
    }

    /**
     * 无参成功返回
     *
     */
    public static <T> ResponseModel<T> success() {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.TRUE);
        response.setCode(String.valueOf(HttpStatus.OK));
        response.setMessage(ResultConstant.SUCCEED);
        response.setResult(null);
        return response;
    }

    /**
     * 成功返回，带业务码
     *
     */
    public static <T> ResponseModel<T> success(String code,T result) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.TRUE);
        response.setCode(code);
        response.setMessage(ResultConstant.SUCCEED);
        response.setResult(result);
        return response;
    }

    /**
     * 成功返回，带业务码和错误信息
     *
     */
    public static <T> ResponseModel<T> success(String code, String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(Boolean.TRUE);
        response.setCode(code);
        response.setMessage(message);
        response.setResult(null);
        return response;
    }

    /**
    * @Description: 统一处理 Response status 为false情况
    */
    public static void checkFailResponse(ResponseModel  responseModel){
        if(ObjectUtil.isNull(responseModel)){
            return;
        }

        if(responseModel.getStatus()==false){
            log.error(responseModel.toString());
            throw new BusinessException(responseModel.getCode(),responseModel.getMessage());
        }
    }


    /**
     * @Description: 统一处理 Response status 为false情况
     */
    public static void checkFailResponse(ResponseModel  responseModel,String rewriteCode,String  rewriteMessage ){
        if(ObjectUtil.isNull(responseModel)){
            return;
        }
        if(responseModel.getStatus()==false){
            log.error(responseModel.toString());
            throw new BusinessException(ObjectUtil.isNull(rewriteCode)?responseModel.getCode():rewriteCode,
                    ObjectUtil.isNull(rewriteMessage)?responseModel.getMessage():rewriteMessage);
        }
    }
}
