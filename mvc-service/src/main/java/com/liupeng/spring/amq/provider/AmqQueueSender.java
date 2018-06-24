package com.liupeng.spring.amq.provider;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * 队列消息生产者
 *
 * @author fengdao.lp
 * @date 2018/6/22
 */
@Service
public class AmqQueueSender {
    @Autowired
    private JmsTemplate jmsQueueTemplate;
    @Autowired
    private JmsTemplate jmsTopicTemplate;
    @Autowired
    private Destination testQueue1;
    @Autowired
    private Destination testQueue2;
    @Autowired
    private Destination testTopic1;
    @Autowired
    private Destination testTopic2;

    /**
     * 生产消息（Queue）
     *
     * @param destinationName 目的地队列名称
     * @param message         消息
     */
    public void sendQueue(String destinationName, final String message) {
        jmsQueueTemplate.send(destinationName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    /**
     * 生产消息（Queue）
     *
     * @param type    业务类型
     * @param message 消息
     */
    public void sendQueue(int type, final String message) {
        Destination destination;
        if (type == 1) {
            destination = testQueue1;
        } else {
            destination = testQueue2;
        }
        jmsQueueTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    /**
     * 发送消息（Topic）
     *
     * @param type    类型
     * @param message 消息
     */
    public void sendTopic(int type, final String message) {
        Destination destination;
        if (type == 1) {
            destination = testTopic1;
        } else {
            destination = testTopic2;
        }
        jmsTopicTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
