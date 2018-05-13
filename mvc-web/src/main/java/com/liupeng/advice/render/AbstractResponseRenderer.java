package com.liupeng.advice.render;

import com.liupeng.advice.vo.Result;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
public abstract class AbstractResponseRenderer implements Render<Result> {

    @Override
    public boolean support(Class clazz) {
        return clazz != null && Result.class.isAssignableFrom(clazz);
    }

    @Override
    public Object render(Result result) {
        Object data;
        if (null == result || (data = result.getData()) == null) {
            return null;
        }
        // 实现类进行渲染
        result.setData(doRender(data));
        return result;
    }

    abstract Object doRender(Object data);
}
