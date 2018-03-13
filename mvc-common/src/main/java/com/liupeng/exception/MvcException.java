package com.liupeng.exception;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
public class MvcException extends RuntimeException {
    private ExceptionCode exceptionCode;

    public MvcException(ExceptionCode exceptionCode, Object... args) {
        super(formatMessage(exceptionCode, args));
        this.exceptionCode = exceptionCode;
    }

    public MvcException(ExceptionCode exceptionCode, Throwable cause, Object... args) {
        super(formatMessage(exceptionCode, args), cause);
        this.exceptionCode = exceptionCode;
    }

    /**
     * 格式化消息内容
     *
     * @param code 错误代码
     * @param args 格式化参数
     * @return 返回结果
     */
    private static String formatMessage(ExceptionCode code, Object... args) {
        if (args.length > 0) {
            return String.format(code.getMessage(), args);
        }
        return code.getMessage();
    }

    public ExceptionCode getCode() {
        return exceptionCode;
    }

}
