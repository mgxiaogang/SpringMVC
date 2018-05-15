package com.liupeng.spring.beanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.liupeng.exception.MvcException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 获取实现了Supportable接口的bean(用于根据不同业务区分处理)
 */
@Service
public class CacheBeansFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private ConcurrentHashMap<Class, List<Supportable>> beansCache = new ConcurrentHashMap<Class, List<Supportable>>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static void setContext(ApplicationContext applicationContext) {
        CacheBeansFactory.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> targetClass, Object factor) {
        if (!beansCache.containsKey(targetClass)) {
            this.cacheBeanByType(targetClass);
        }
        List<Supportable> supportables = beansCache.get(targetClass);
        List<Supportable> result = supportables.stream().filter(t -> t.supports(factor)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(result)) {
            return (T)result.get(0);
        }
        throw new MvcException(String.format("无法找到bean:%s,仅支持实现了Supportable接口的类", targetClass.getName()));
    }

    private <T> void cacheBeanByType(Class<T> targetClass) {
        // 根据bean的class来查找State的所有对象(包括子类)
        Map<String, T> beans = applicationContext.getBeansOfType(targetClass);
        List<Supportable> supportableList = new ArrayList<Supportable>();
        if (CollectionUtils.isNotEmpty(beans.values())) {
            beans.values().forEach(t -> {
                if (Supportable.class.isAssignableFrom(t.getClass())) {
                    Supportable supportable = (Supportable)t;
                    supportableList.add(supportable);
                }
            });
        }
        beansCache.put(targetClass, supportableList);
    }
}
