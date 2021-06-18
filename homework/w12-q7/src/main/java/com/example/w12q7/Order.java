package com.example.w12q7;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@NoArgsConstructor
@Data
@Entity
@Table(name = "purchase_order")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @lombok.NonNull
    private int status;

    @lombok.NonNull
    private Date createdTime;

    @lombok.NonNull
    private Date updatedTime;

    private int consumerId;


    public Order(final int status) {

        this.status = status;
        this.createdTime = new Date();
    }
}
