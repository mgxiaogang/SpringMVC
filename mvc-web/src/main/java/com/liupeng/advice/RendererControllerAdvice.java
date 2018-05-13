package com.liupeng.advice;

import java.util.List;

import com.liupeng.advice.annotation.Rendered;
import com.liupeng.advice.render.Render;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author fengdao.lp
 * @date 2018/3/13
 */
@ControllerAdvice
@Service
public class RendererControllerAdvice implements ResponseBodyAdvice {
    private final Logger logger = LoggerFactory.getLogger(RendererControllerAdvice.class);

    @Autowired
    private List<Render> renderList;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 获取当前处理请求的controller的方法
        String methodName = methodParameter.getMethod().getName();
        // 不需要拦截的方法
        String method = "loginCheck";
        // 不拦截
        return !methodName.equalsIgnoreCase(method);
    }

    /**
     * 对返回值统一渲染
     *
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        long startTime = System.currentTimeMillis();
        Object result = o;
        Rendered rendered = methodParameter.getMethod().getAnnotation(Rendered.class);
        if (rendered == null) {
            return o;
        }
        for (Render render : renderList) {
            if (null != render && render.support(methodParameter.getParameterType())) {
                result = render.render(result);
            }
        }
        logger.debug("render cost time:{}ms, invoke method:{}", (System.currentTimeMillis() - startTime),
            methodParameter.getMethod());
        return result;
    }
}
