package com.dailycodebuffer.stockservice.runners;

import com.dailycodebuffer.stockservice.entity.Stock;
import com.dailycodebuffer.stockservice.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeanOne implements CommandLineRunner {

    @Autowired private StockRepository stockRepository;

    public void run(String... args) {
        log.info("Starting sample insertion of stocks in database");
        stockRepository.save(new Stock("stock1", 623.23, 800L));
        stockRepository.save(new Stock("stock2", 523.23, 800L));
        stockRepository.save(new Stock("stock3", 493.23, 800L));
        stockRepository.save(new Stock("stock4", 503.23, 800L));
        log.info("# of stocks {}", stockRepository.count());
        System.out.println("Stocks set in database");
    }
}
