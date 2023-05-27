package com.dailycodebuffer.stockservice.dto;

import lombok.Data;

@Data
public class OrderRequest {
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

        public String getOrderTypeInString() {
            return this.orderType;
        }
    }
}
