package com.example.w12q8;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class W12Q8Application {

    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(W12Q8Application.class, args);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
        orderRepository.deleteAll();

        Producer producer = beanFactory.getBean(Producer.class);
        Thread tp = new Thread(producer);
        tp.start();
        tp.join();
    }
}
