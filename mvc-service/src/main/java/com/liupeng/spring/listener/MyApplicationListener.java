package com.liupeng.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 实现接口监听
 *
 * @author fengdao.lp
 * @date 2018/5/15
 */
@Service
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    private final Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        logger.info("实现接口方式监听自定义事件：" + event.getMessage());
    }
}
