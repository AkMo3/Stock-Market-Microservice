package com.dailycodebuffer.stockservice.dto;

import com.dailycodebuffer.stockservice.entity.Order;
import lombok.Data;

@Data
public class OrderRequest {
    Long stockId;
    Integer quantity;
    Long customerId;
    OrderRequest.OrderType orderType;
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
