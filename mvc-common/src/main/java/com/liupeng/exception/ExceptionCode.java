package com.liupeng.exception;

/**
 * @author fengdao.lp
 * @date 2018/3/1
 */
public enum ExceptionCode {
    OTHER("未知的错误"),
    TOKEN_NOT_EXIST("token不存在"),
    PARAM_VALIDATE_ERROR("参数不合法[%s]"),
    SAVE_FAIL("保存失败");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

