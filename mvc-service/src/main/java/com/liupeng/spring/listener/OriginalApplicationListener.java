package com.liupeng.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class OriginalApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LoggerFactory.getLogger(OriginalApplicationListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            logger.error(String.format("beanName:=%s, class:=%s", name, bean.getClass()));
        }
    }
}
