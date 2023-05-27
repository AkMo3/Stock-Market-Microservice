package com.dailycodebuffer.user.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Order(value=2)
@Slf4j
public class BeanTwo implements CommandLineRunner {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable makeRequestToStockServer = new RequestDispatch(restTemplate, log);
        executorService.scheduleAtFixedRate(makeRequestToStockServer, 180, 15, TimeUnit.SECONDS);
    }
}

class RequestDispatch implements Runnable {

    private final RestTemplate restTemplate;
    private final Logger logger;
    private final List<Long> userIds = List.of(1L, 2L, 3L, 4L);
    private static final Integer MAX_QUANTITY = 1000;
    ObjectMapper mapper = new ObjectMapper();
    private static final int REQUEST_PER_ITERATION = 20;

    RequestDispatch(RestTemplate restTemplate, Logger logger) {
        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    @Override
    public void run() {

        for (int i = 0; i < REQUEST_PER_ITERATION; i++) {
            Long customerId = (long) (Math.random() * (4) + 1);
            Integer quantity = (int) (Math.random() * (1000));
            Long stockId = (long) (Math.random() * (4) + 1);
            double stockPrice = Math.random() * (500) + 300;
            double buyBias = Math.random();
            com.dailycodebuffer.user.VO.Order order = new com.dailycodebuffer.user.VO.Order();
            order.setQuantity(quantity);
            order.setCustomerId(customerId);
            order.setStockId(stockId);
            order.setStockPrice(stockPrice);

            try {
                System.out.println("Request: \n" + mapper.writeValueAsString(order));
            }
            catch (Exception e) {
                // Do noting
            }

            if (buyBias < 0.6) {
                order.setOrderType("Buy");
            }
            else {
                order.setOrderType("Sell");
            }
            logger.info("Sending request to order stock");
            ResponseEntity<com.dailycodebuffer.user.VO.Order> receivedOrder =
                    restTemplate.postForEntity("http://STOCK-SERVICE/stock/order", order,
                            com.dailycodebuffer.user.VO.Order.class);
            logger.info("Order status {}", receivedOrder.getStatusCode().value());
        }
    }
}
