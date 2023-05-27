package com.dailycodebuffer.stockservice.entity;

import com.dailycodebuffer.stockservice.dto.StockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stock {

    public Stock(StockDto dto) {
        this.stockName = dto.getStockName();
        this.stockPrice = dto.getStockPrice();
        this.volume = dto.getVolume();
    }

    public Stock(String stockName, Double stockPrice, Long volume) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.volume = volume;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stockId;
    private Double stockPrice;
    private String stockName;
    private Long volume;
}
