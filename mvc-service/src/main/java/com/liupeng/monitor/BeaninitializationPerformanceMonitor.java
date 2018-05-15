package com.liupeng.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class BeaninitializationPerformanceMonitor implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(BeaninitializationPerformanceMonitor.class);

    private Map<String, Long> staticsMaps = new ConcurrentHashMap<String, Long>();
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        staticsMaps.put(beanName, System.currentTimeMillis());
        logger.error("id={},class={}", beanName, bean.getClass().getName());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (staticsMaps.get(beanName) != null) {
            Long t = System.currentTimeMillis() - staticsMaps.get(beanName);
            logger.error("bean初始化,id={},class={},t={}ms", beanName, bean.getClass().getName(), t);
        }
        return bean;
    }
}
