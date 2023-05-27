package com.dailycodebuffer.user.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    private Long stockId;
    private Double stockPrice;
    private String stockName;
    private Integer volume;
}
