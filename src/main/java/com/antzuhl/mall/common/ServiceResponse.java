package com.antzuhl.mall.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.crypto.Data;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> {
    private int code;
    private String msg;
    private T data;

    private ServiceResponse(int code) {
        this.code = code;
    }

    private ServiceResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private ServiceResponse(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
    private ServiceResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServiceResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ServiceResponse<T> createSuccessResponse() {
        return new ServiceResponse<>(HResult.R_OK.getCode(),
                HResult.R_OK.getMsg());
    }

    public static <T> ServiceResponse<T> createSuccessResponse(String msg) {
        return new ServiceResponse<>(HResult.R_OK.getCode(), msg);
    }

    public static <T> ServiceResponse<T> createSuccessResponse(T data) {
        return new ServiceResponse<>(HResult.R_OK.getCode(),
                HResult.R_OK.getMsg(), data);
    }

    public static <T> ServiceResponse<T> createSuccessResponse(String msg, T data) {
        return new ServiceResponse<>(HResult.R_OK.getCode(),
                msg, data);
    }

    public static <T> ServiceResponse<T> createErrorResponse() {
        return new ServiceResponse<>(HResult.R_ERROR.getCode());
    }

    public static <T> ServiceResponse<T> createErrorResponse(String msg) {
        return new ServiceResponse<>(HResult.R_ERROR.getCode(), msg);
    }

    public static <T> ServiceResponse<T> createErrorResponse(T data) {
        return new ServiceResponse<>(HResult.R_ERROR.getCode(), HResult.R_ERROR.getMsg(), data);
    }

    public Boolean isSuccess() {
        return this.code == HResult.R_OK.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
