package com.liupeng.spring.amq.consumer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试接收Queue消息
 *
 * @author fengdao.lp
 * @date 2018/6/22
 */
public class TestQueueConsumer implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(TestQueueConsumer.class);

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            logger.error("此测试类只支持TextMessage");
        }
        try {
            String str = ((TextMessage)message).getText();
            System.out.println("消息参数为：" + str);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
