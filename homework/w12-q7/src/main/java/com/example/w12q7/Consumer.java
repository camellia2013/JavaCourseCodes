package com.example.w12q7;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class Consumer implements Runnable {

    private static boolean processing = false;

    private int id;

    @Autowired
    private OrderRepository orderRepository;


    public Consumer(final int id, final OrderRepository orderRepository) {

        this.id = id;
        this.orderRepository = orderRepository;
    }


    public Consumer() {

        this.id = 0;
    }


    @Override
    public void run() {

        consume();
    }


    @Scheduled(cron = "1/10 * * * * *")
    public void consume() {

        if (processing) {
            log.info("==> consumer [{}] skip", id);
            return;
        }

        log.info("[{}] consuming ...", id);
        processing = true;
        long idBefore = orderRepository.findLargestId();
        if (W12Q7Application.latestId.get() == idBefore) {
            log.info("other consumer is doing");
            return;
        }
        W12Q7Application.latestId.set(idBefore);
        List<Order> orders = orderRepository.findByStatusAndIdIsBefore(0, idBefore).stream().peek(o -> {
            log.info("consumer [{}] consume order: {}", id, o);
            o.setStatus(1);
            o.setUpdatedTime(new Date());
            o.setConsumerId(id);
        }).collect(Collectors.toList());
        orderRepository.saveAll(orders);
        processing = false;
        log.info("done consume [{}]", id);
    }
}
