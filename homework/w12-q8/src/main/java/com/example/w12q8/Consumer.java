package com.example.w12q8;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Slf4j
@Service
public class Consumer {

    @Autowired
    private OrderRepository orderRepository;


    @JmsListener(destination = "test.queue")
    public void consume0(Long orderId) {

        process(orderId, 0);
    }


    private void process(final Long orderId, final int consumerId) {

        Optional<Order> optional = orderRepository.findByIdAndStatus(orderId, 0);
        if (!optional.isPresent()) {
            return;
        }
        Order o = optional.get();
        log.info("consumer [{}] consume order: {}", 0, orderId);
        o.setStatus(1);
        o.setUpdatedTime(new Date());
        o.setConsumerId(consumerId);
        orderRepository.save(o);
    }


    @JmsListener(destination = "test.queue")
    public void consume1(Long orderId) {

        process(orderId, 1);
    }


    @JmsListener(destination = "test.queue")
    public void consume2(Long orderId) {

        process(orderId, 2);
    }
}
