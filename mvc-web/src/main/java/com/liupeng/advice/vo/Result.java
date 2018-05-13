package com.liupeng.advice.vo;

import java.io.Serializable;

public class Result<T> implements ResultCode, Serializable {
    private static final long serialVersionUID = 878789487962428351L;

    private T data;
    private int code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == OK;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> Result<T> buildSuccessResultOf(T data) {
        return buildResultOf(OK, data);
    }

    public static <T> Result<T> buildResultOf(int code, T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> buildFailedResultOf(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("data=").append(data);
        sb.append(", code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}
