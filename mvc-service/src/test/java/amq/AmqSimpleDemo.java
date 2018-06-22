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
import javax.jms.Topic;

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
        Destination destination = new ActiveMQQueue("testQueue");
        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            connection.start();

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4.创建生产者，并指定目的地
            MessageProducer messageProducer = session.createProducer(destination);

            // 5.发送消息到目的地
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("userid", "123456");
            mapMessage.setString("username", "刘鹏");
            mapMessage.setInt("age", 26);
            messageProducer.send(mapMessage);

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
     * 生产者 - topic
     */
    @Test
    public void testAmqTopicProvider() {
        // 1.连接工厂broker
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "failover://(tcp://116.62.225.170:61616)?randomize=false&jms.useAsyncSend=true&timeout=10000");

        // 创建生产者，指定队列
        Destination destination = new ActiveMQTopic("testTopic");

        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            connection.start();

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4.创建生产者，并指定目的地
            MessageProducer messageProducer = session.createProducer(destination);

            // 5.发送消息到目的地
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("userid", "123456");
            mapMessage.setString("username", "刘鹏");
            mapMessage.setInt("age", 26);
            messageProducer.send(mapMessage);

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
        Destination destination = new ActiveMQQueue("testQueue");
        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            connection.start();

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4.创建生产者，并指定目的地
            MessageConsumer messageConsumer = session.createConsumer(destination);

            // 5.消费者从目的地获取消息
            MapMessage mapMessageQueue = (MapMessage)messageConsumer.receive();
            System.out.println(mapMessageQueue.getString("username"));
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println(((MapMessage)message).getString("username"));
                    } catch (JMSException e) {
                        System.out.println("error");
                    }
                }
            });
            Thread.sleep(5000);

            // 6.关闭
            //connection.close();
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
     * 消费者 - topic
     */
    @Test
    public void testAmqTopicConsumer() {
        // 1.连接工厂broker
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            "failover://(tcp://116.62.225.170:61616)?randomize=false&jms.useAsyncSend=true&timeout=10000");

        // 创建生产者，指定队列
        Topic destinationTopic = new ActiveMQTopic("testTopic");

        Connection connection = null;
        try {
            // 2.通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            connection.setClientID("Client-123456");  // 指定clientID用于持久化订阅

            // 3.通过连接创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            // 4.创建生产者，并指定目的地
            //MessageConsumer messageConsumer = session.createConsumer(destinationTopic);
            MessageConsumer messageConsumer = session.createDurableSubscriber(destinationTopic,
                "subscriber-20180622");

            // 5.消费者从目的地获取消息
            messageConsumer.setMessageListener(new MessageListener() {
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
            Thread.sleep(10000);

            // 6.关闭
            //connection.close();
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