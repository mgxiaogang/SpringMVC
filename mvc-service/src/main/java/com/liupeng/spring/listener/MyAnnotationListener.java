package com.liupeng.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 注解方式监听
 *
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class MyAnnotationListener {
    private final Logger LOGGER = LoggerFactory.getLogger(MyApplicationListener.class);

    @EventListener
    public void listener(MyApplicationEvent event) {
        LOGGER.info("注解方式同步监听自定义事件：" + event.getMessage());
    }

    @EventListener
    @Async
    public void listener2(MyApplicationEvent event) {
        LOGGER.info("注解方式异步监听自定义事件：" + event.getMessage());
    }
}
