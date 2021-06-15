package com.example.demo;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;


// @SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        // 定义Destination
        // Destination destination = new ActiveMQTopic("test.topic");
        // testDestination(destination);
        // testTopicDestination(destination);

        Destination destination = new ActiveMQQueue("test.queue");
        testQueueDestination(destination);
        // SpringApplication.run(DemoApplication.class, args);
    }


    public static void testQueueDestination(Destination destination) {

        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            final AtomicInteger count = new AtomicInteger(0);
            for (int i : new int[]{1, 2, 3}) {
                MessageConsumer consumer = session.createConsumer(destination);
                MessageListener listener = message -> {
                    try {
                        // 打印所有的消息内容
                        // Thread.sleep();
                        System.out.println(count.incrementAndGet() + " => " + "consumer" + i + " => receive from " + destination.toString() + ": " + message);
                        // message.acknowledge(); // 前面所有未被确认的消息全部都确认。

                    } catch (Exception e) {
                        e.printStackTrace(); // 不要吞任何这里的异常，
                    }
                };
                // 绑定消息监听器
                consumer.setMessageListener(listener);
            }

            // 创建生产者，生产100个消息
            System.out.println("producing ...");
            MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage message = session.createTextMessage(index + " message.");
                producer.send(message);
            }

            Thread.sleep(2000);

            session.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testTopicDestination(Destination destination) {

        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            final AtomicInteger count = new AtomicInteger(0);
            for (int i : new int[]{1, 2, 3}) {
                MessageConsumer consumer = session.createConsumer(destination);
                MessageListener listener = message -> {
                    try {
                        // 打印所有的消息内容
                        // Thread.sleep();
                        System.out.println(count.incrementAndGet() + " => " + "consumer" + i + " => receive from " + destination.toString() + ": " + message);
                        // message.acknowledge(); // 前面所有未被确认的消息全部都确认。

                    } catch (Exception e) {
                        e.printStackTrace(); // 不要吞任何这里的异常，
                    }
                };
                // 绑定消息监听器
                consumer.setMessageListener(listener);
            }

            // 创建生产者，生产100个消息
            System.out.println("producing ...");
            MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage message = session.createTextMessage(index + " message.");
                producer.send(message);
            }

            Thread.sleep(2000);

            session.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testDestination(Destination destination) {

        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            final AtomicInteger count = new AtomicInteger(0);
            MessageListener listener = message -> {

                try {
                    // 打印所有的消息内容
                    // Thread.sleep();
                    System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
                    // message.acknowledge(); // 前面所有未被确认的消息全部都确认。

                } catch (Exception e) {
                    e.printStackTrace(); // 不要吞任何这里的异常，
                }
            };
            // 绑定消息监听器
            consumer.setMessageListener(listener);

            // 没有listener的话, 这里会阻塞, 一直等待接收
            // Message m = consumer.receive();
            // System.out.println("Received: " + m);

            // 创建生产者，生产100个消息
            System.out.println("producing ...");
            MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage message = session.createTextMessage(index + " message.");
                producer.send(message);
            }

            Thread.sleep(2000);

            // 没有listener的话, 这里只能接收一个消息
            // Message m = consumer.receive();
            // System.out.println("Received: " + m);

            session.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
