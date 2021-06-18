package com.example.w12q8;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByIdAndStatus(long id, int status);
}
