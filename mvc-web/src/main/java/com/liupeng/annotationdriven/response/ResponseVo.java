package com.liupeng.annotationdriven.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class ResponseVo implements ResponseData {

    // 返回表示符
    private boolean success = true;

    // 错误code
    private int errorCode = 1;

    // 错误信息
    private String msg = "成功";

    // 返回信息
    private Object data;

    public void setData(Object rows, int count, boolean flag) {
        if (flag) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("count", count);
            this.data = map;
        } else {
            this.data = rows;
        }
    }

    public ResponseVo() {
    }

    public ResponseVo(Object data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }
}
