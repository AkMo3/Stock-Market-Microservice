package com.dailycodebuffer.stockservice.controller;

import com.dailycodebuffer.stockservice.dto.OrderRequest;
import com.dailycodebuffer.stockservice.entity.StockOrder;
import com.dailycodebuffer.stockservice.dto.StockDto;
import com.dailycodebuffer.stockservice.entity.Stock;
import com.dailycodebuffer.stockservice.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@Slf4j
@AllArgsConstructor
public class StockController {

    public final StockService stockService;
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/")
    public Stock saveStock(StockDto stockDto) {
        log.info("Stock save request received");
        return stockService.saveStock(stockDto);
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable("id") Long stockId) {
        log.info("Received request to find stock");
        return stockService.findStockById(stockId);
    }

    @PostMapping("/order")
    public ResponseEntity<StockOrder> executeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            System.out.println(" Request: \n" + mapper.writeValueAsString(orderRequest));
        }
        catch (Exception e) {
            // Do noting
        }
        StockOrder stockOrder = stockService.executeOrder(orderRequest);
        return ResponseEntity.ok(stockOrder);
    }

    @PostMapping("/name")
    public ResponseEntity<List<String>> getNamesOfStock(List<Long> stockIds) {
        List<String> stockNames = stockService.getNamesForStocks(stockIds);
        return ResponseEntity.ok(stockNames);
    }
}
