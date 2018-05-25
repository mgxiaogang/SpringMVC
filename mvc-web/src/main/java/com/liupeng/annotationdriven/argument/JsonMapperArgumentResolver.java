package com.liupeng.annotationdriven.argument;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.controller.annotation.JSON;
import com.liupeng.domain.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 参数解析器
 *
 * @author fengdao.lp
 * @date 2018/5/25
 */
public class JsonMapperArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapperArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JSON.class) && parameter.getParameterType().equals(UserVO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            JSON json = parameter.getParameterAnnotation(JSON.class);
            String allParam = getAllParam(webRequest);
            if (StringUtils.isBlank(allParam)) {
                return null;
            }
            String name = webRequest.getParameter("name");
            String date = webRequest.getParameter("date");
            if (json != null) {
                String param = json.ignore();
                if (param.equalsIgnoreCase("age")) {
                    return new UserVO(0, name, DateUtils.parseDate(date, "yyyy-MM-dd"));
                } else {
                    return new UserVO(26, name, DateUtils.parseDate(date, "yyyy-MM-dd"));
                }
            }
        } catch (Exception e) {
            LOGGER.error("参数解析失败", e);
        }
        return null;
    }

    /**
     * 根据请求request获取参数
     *
     * @param webRequest 请求request
     * @return 请求参数
     */
    private String getAllParam(NativeWebRequest webRequest) throws IOException {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String method = httpServletRequest.getMethod();
        if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            return httpServletRequest.getQueryString();
        }
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = httpServletRequest.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
