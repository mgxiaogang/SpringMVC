package com.liupeng.annotationdriven.response;

/**
 * 无需包装的Response数据结构,如果返回类型是此类型，不做任务包装处理
 *
 * @author fengdao.lp
 * @date 2018/5/24
 */
public interface ResponseData {
    boolean isSuccess();
}
