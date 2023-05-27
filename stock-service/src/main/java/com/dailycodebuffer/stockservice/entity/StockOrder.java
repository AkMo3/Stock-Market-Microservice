package com.dailycodebuffer.stockservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class StockOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    Long stockId;
    Integer quantity;
    Long customerId;
    String orderType;
    Double stockPrice;

    public enum OrderType {
        Buy("Buy"),
        Sell("Sell");

        private final String orderType;

        OrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderType() {
            return this.orderType;
        }
    }
}
