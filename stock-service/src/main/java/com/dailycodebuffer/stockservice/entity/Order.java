package com.dailycodebuffer.stockservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    Long stockId;
    Integer quantity;
    Long customerId;
    OrderType orderType;
    Double stockPrice;

    public enum OrderType {
        Buy, Sell;
    }
}
