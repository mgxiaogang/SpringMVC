package com.liupeng.annotationdriven.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.liupeng.annotationdriven.response.SelfResponseJson.Location;
import com.liupeng.annotationdriven.response.wrapper.BeanWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author fengdao.lp
 * @date 2018/5/24
 */
public class ResponseJsonMethodProcessor implements HandlerMethodReturnValueHandler, InitializingBean {

    private HttpMessageConverter messageConverter;

    private List<BeanWrapper> beanWrappers;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("==============================");
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(SelfResponseJson.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        Object result = returnValue;
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);

        if (null == returnValue) {
            Map<String, Object> message = new HashMap<String, Object>();
            message.put("success", true);
            message.put("data", new HashMap<String, String>());
            result = message;
        } else {
            // 可以考虑换别的注解增加参数来校验一些内容
            SelfResponseJson selfResponseJson = returnType.getMethodAnnotation(SelfResponseJson.class);
            if (selfResponseJson.location() == Location.MESSAGE) {
                Map<String, Object> message = new HashMap<String, Object>();
                message.put("success", true);
                message.put("msg", returnValue);
                result = message;
            } else {
                //String header =  webRequest.getHeader("Cookie");
                //outputMessage.getHeaders().set("alibaba","json/text");
                for (BeanWrapper beanWrapper : beanWrappers) {
                    if (beanWrapper.supportsType(returnType)) {
                        result = beanWrapper.wrap(returnValue);
                        break;
                    }
                }
            }
        }
        messageConverter.write(result, new MediaType(MediaType.APPLICATION_JSON, Collections
            .singletonMap("charset", "UTF-8")), outputMessage);

    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public List<BeanWrapper> getBeanWrappers() {
        return beanWrappers;
    }

    public void setBeanWrappers(List<BeanWrapper> beanWrappers) {
        this.beanWrappers = beanWrappers;
    }
}
