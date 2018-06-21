package amq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;

/**
 * @author fengdao.lp
 * @date 2018/6/21
 */
public class AmqSimpleDemo {

    /**
     * 生产者
     */
    @Test
    public void testAmqProvider() {
        // 1.连接工厂broker
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "failover://(tcp://116.62.225.170:61616)?randomize=false&jms.useAsyncSend=true&timeout=10000");

        // 创建生产者，指定队列
        Destination destinationQueue = new ActiveMQQueue("testQueueDestination");
        Destination destinationTopic = new ActiveMQTopic("testTopicDestination");

        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4.创建生产者，并指定目的地
            MessageProducer messageProducerQueue = session.createProducer(destinationQueue);
            MessageProducer messageProducerTopic = session.createProducer(destinationTopic);

            // 5.发送消息到目的地
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("userid", "123456");
            mapMessage.setString("username", "刘鹏");
            mapMessage.setInt("age", 26);
            messageProducerQueue.send(mapMessage);
            messageProducerTopic.send(mapMessage);

            // 6.关闭
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e2) {
                    System.out.println("error");
                }
            }
        }
    }

    /**
     * 消费者
     */
    @Test
    public void testAmqConsumer() {
        // 1.连接工厂broker
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "failover://(tcp://116.62.225.170:61616)?randomize=false&jms.useAsyncSend=true&timeout=10000");

        // 创建生产者，指定队列
        Destination destinationQueue = new ActiveMQQueue("testQueueDestination");
        Destination destinationTopic = new ActiveMQTopic("testTopicDestination");

        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4.创建生产者，并指定目的地
            MessageConsumer messageConsumerQueue = session.createConsumer(destinationQueue);
            MessageConsumer messageConsumerTopic = session.createConsumer(destinationTopic);

            // 5.消费者从目的地获取消息
            connection.start();
            MapMessage mapMessageQueue = (MapMessage)messageConsumerQueue.receive();
            System.out.println(mapMessageQueue.getString("username"));
            messageConsumerTopic.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    MapMessage mapMessageTopic = (MapMessage)message;
                    try {
                        System.out.println(mapMessageTopic.getString("username"));
                    } catch (JMSException e) {
                        System.out.println("error");
                    }
                }
            });

            // 6.关闭
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e2) {
                    System.out.println("error");
                }
            }
        }
    }
}
