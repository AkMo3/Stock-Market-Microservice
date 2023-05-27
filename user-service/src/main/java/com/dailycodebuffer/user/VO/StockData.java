package com.dailycodebuffer.user.VO;

import com.dailycodebuffer.user.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class StockData {
    private User user;
    private List<Stock> stockList;
}
