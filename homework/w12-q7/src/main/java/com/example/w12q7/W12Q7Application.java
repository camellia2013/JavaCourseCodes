package com.example.w12q7;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.atomic.AtomicLong;


/**
 * 7.（选做）基于数据库的订单表，模拟消息队列处理订单：
 * <p>
 * 一个程序往表里写新订单，标记状态为未处理 (status=0);
 * 另一个程序每隔 100ms 定时从表里读取所有 status=0 的订单，打印一下订单数据，然后改成完成 status=1；
 * （挑战☆）考虑失败重试策略，考虑多个消费程序如何协作。
 */

@SpringBootApplication
@EnableScheduling
public class W12Q7Application {

    // 缓存, 用redis更好, 多个消费程序协作
    public static AtomicLong latestId = new AtomicLong(1);


    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(W12Q7Application.class, args);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
        orderRepository.deleteAll();

        Producer producer = beanFactory.getBean(Producer.class);
        Thread tp = new Thread(producer);
        tp.start();
        tp.join();

        // 单个消费者


        // 3个消费者
        Consumer c1 = new Consumer(1, orderRepository);
        c1.consume();
        Consumer c2 = new Consumer(2, orderRepository);
        c2.consume();
    }
}
