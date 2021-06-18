package com.example.w12q7;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByStatusAndIdIsBefore(int status, long idAfter);

    @Query(value = "select max(id)+1 from purchase_order", nativeQuery = true)
    long findLargestId();
}
