package com.dailycodebuffer.stockservice.dto;

import lombok.Data;

@Data
public class StockDto {
    private double stockPrice;
    private String stockName;
    private long volume;
}
