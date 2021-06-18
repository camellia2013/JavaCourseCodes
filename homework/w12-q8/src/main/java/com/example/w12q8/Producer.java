package com.example.w12q8;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Random;


@Slf4j
@Service
public class Producer implements Runnable {

    private final Destination destination = new ActiveMQQueue("test.queue");

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private OrderRepository orderRepository;




    @Override
    public void run() {

        try {
            produce();
        } catch (InterruptedException e) {
            log.error("Failed", e);
        }
    }


    public void produce() throws InterruptedException {

        log.info("producing ...");
        Random r = new Random();
        int count = 100;
        while (count > 0) {

            Order order = new Order(0);
            order = orderRepository.save(order);
            jmsTemplate.convertAndSend(destination, order.getId());
            count--;
            Thread.sleep(r.nextInt(100));
        }
        log.info("done produce");
    }
}
