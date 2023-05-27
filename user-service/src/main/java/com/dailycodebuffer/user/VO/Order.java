package com.dailycodebuffer.user.VO;

import lombok.Data;

@Data
public class Order {
    Long stockId;
    Integer quantity;
    Long customerId;
    OrderType orderType;
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
