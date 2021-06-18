package com.example.w12q7;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Slf4j
@Service
public class Producer implements Runnable {

    @Autowired
    private OrderRepository orderRepository;


    public void produce() throws InterruptedException {

        log.info("producing ...");
        Random r = new Random();
        int count = 100;
        while (count > 0) {

            Order order = new Order(0);
            orderRepository.save(order);
            count--;
            Thread.sleep(r.nextInt(100));
        }
        log.info("done produce");
    }


    @Override
    public void run() {

        try {
            produce();
        } catch (InterruptedException e) {
            log.error("Failed", e);
        }
    }
}
