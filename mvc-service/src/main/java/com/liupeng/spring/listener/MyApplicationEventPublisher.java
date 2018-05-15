package com.liupeng.spring.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 发布事件
 *
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class MyApplicationEventPublisher implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyApplicationEventPublisher.applicationContext = applicationContext;
    }

    public void publish(String message) {
        applicationContext.publishEvent(new MyApplicationEvent(this, message));
    }
}
