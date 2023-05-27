package com.dailycodebuffer.stockservice.service;

import com.dailycodebuffer.stockservice.dto.OrderRequest;
import com.dailycodebuffer.stockservice.entity.StockOrder;
import com.dailycodebuffer.stockservice.dto.StockDto;
import com.dailycodebuffer.stockservice.entity.Stock;
import com.dailycodebuffer.stockservice.exception.InvalidCustomerException;
import com.dailycodebuffer.stockservice.exception.InvalidStockException;
import com.dailycodebuffer.stockservice.repository.OrderRepository;
import com.dailycodebuffer.stockservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public Stock saveStock(StockDto stockDto) {
        Stock newStock = new Stock(stockDto);
        log.info("Saving new stock: " + stockDto.getStockName() + " to database");
        return stockRepository.save(newStock);
    }

    public Stock findStockById(Long stockId) throws InvalidStockException {
        log.info("Finding stock with id: " + stockId);
        return stockRepository.findByStockId(stockId).orElseThrow(() -> new InvalidStockException("No stock with id " + stockId + " is found"));
    }

    public List<String> getNamesForStocks(List<Long> stockIds) throws InvalidStockException {
        ArrayList<String> names = new ArrayList<>();
        for(long stockId: stockIds) {
            names.add(findStockById(stockId).getStockName());
        }
        return names;
    }

    public StockOrder executeOrder(@RequestBody OrderRequest order) throws InvalidCustomerException, InvalidStockException {
        String orderType = order.getOrderType();
        Stock stock = findStockById(order.getStockId());
        ResponseEntity<Boolean> customer = restTemplate.getForEntity("http://USER-SERVICE/users/exists/" + order.getCustomerId(),
                Boolean.class);
        if (customer.getStatusCode() != HttpStatus.OK || customer.getBody().equals(Boolean.FALSE))
            throw new InvalidCustomerException("No customer found with id: {" + order.getCustomerId() + "}");
        double newPrice;
        if (orderType.equals("Buy")) {
            newPrice = (order.getStockPrice() * -stock.getStockPrice()) * 0.1 + stock.getStockPrice();
        }
        else {
            newPrice = (order.getStockPrice() * -stock.getStockPrice()) * 0.1 - stock.getStockPrice();
        }
        stock.setStockPrice(newPrice);

        long count = orderRepository.count();
        StockOrder customerStockOrder = new StockOrder();
        customerStockOrder.setOrderId(count + 1);
        customerStockOrder.setCustomerId(order.getCustomerId());
        customerStockOrder.setOrderType(orderType);
        customerStockOrder.setStockId(order.getStockId());
        customerStockOrder.setQuantity(order.getQuantity());
        customerStockOrder.setStockPrice(order.getStockPrice());

        customerStockOrder = orderRepository.save(customerStockOrder);
        ResponseEntity<Boolean> result =
                restTemplate.postForEntity("http://USER-SERVICE/users/transaction/", customerStockOrder, Boolean.class);

        if (result.getStatusCode() != HttpStatus.ACCEPTED) throw new InvalidCustomerException("Some error occurred");
        return customerStockOrder;
    }
}
