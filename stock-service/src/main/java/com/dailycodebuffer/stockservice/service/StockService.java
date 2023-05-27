package com.dailycodebuffer.stockservice.service;

import com.dailycodebuffer.stockservice.dto.Customer;
import com.dailycodebuffer.stockservice.dto.OrderRequest;
import com.dailycodebuffer.stockservice.entity.Order;
import com.dailycodebuffer.stockservice.dto.StockDto;
import com.dailycodebuffer.stockservice.entity.Stock;
import com.dailycodebuffer.stockservice.exception.InvalidCustomerException;
import com.dailycodebuffer.stockservice.exception.InvalidStockException;
import com.dailycodebuffer.stockservice.repository.OrderRepository;
import com.dailycodebuffer.stockservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public Order executeOrder(OrderRequest order) throws InvalidCustomerException, InvalidStockException {
        OrderRequest.OrderType orderType = order.getOrderType();
        Stock stock = findStockById(order.getStockId());
        Customer customer = restTemplate.getForObject("http://USER-SERVICE/users/" + order.getCustomerId(),
                Customer.class);
        if (customer == null) throw new InvalidCustomerException("No customer found with id: {" + order.getCustomerId() + "}");
        double newPrice;
        if (orderType.equals(OrderRequest.OrderType.Buy)) {
            newPrice = (order.getStockPrice() * -stock.getStockPrice()) * 0.1 + stock.getStockPrice();
        }
        else {
            newPrice = (order.getStockPrice() * -stock.getStockPrice()) * 0.1 - stock.getStockPrice();
        }
        stock.setStockPrice(newPrice);

        Order customerOrder = new Order();
        customerOrder.setCustomerId(order.getCustomerId());
        customerOrder.setOrderType(order.getOrderType() == OrderRequest.OrderType.Buy ? Order.OrderType.Buy : Order.OrderType.Sell);
        customerOrder.setStockId(order.getStockId());
        customerOrder.setQuantity(order.getQuantity());
        customerOrder.setStockPrice(order.getStockPrice());

        return orderRepository.save(customerOrder);
    }
}
