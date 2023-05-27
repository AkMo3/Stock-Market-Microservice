package com.dailycodebuffer.user.service;

import com.dailycodebuffer.user.VO.*;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.entity.UserStockData;
import com.dailycodebuffer.user.repository.UserRepository;
import com.dailycodebuffer.user.repository.UserStockDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserStockDataRepository userStockDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId) throws NoSuchElementException {
        return userRepository.findByUserId(userId).orElseThrow();
    }

    public StockData getUserWithStocks(Long userId) throws NoSuchElementException {
        log.info("Inside getUserWithDepartment of UserService");
        User user = userRepository.findByUserId(userId).orElseThrow();

        ArrayList<Long> userStocksId = new ArrayList<>();
        List<UserStockData> userStockDataList = userStockDataRepository.findAllByUser(user);
        userStockDataList.forEach(e -> userStocksId.add(e.getStockId()));

        StockData stockData = new StockData();
        stockData.setUser(user);

        List<Stock> userStocks = new ArrayList<>();
        ResponseEntity<List> stockNameResponse = restTemplate.postForEntity("http://STOCK-SERVICE/stock/name", userStocksId, List.class);
        if (stockNameResponse.getStatusCode() != HttpStatus.OK) {
            throw new NoSuchElementException("Invalid request");
        }
        List<String> stockNames = (List<String>) stockNameResponse.getBody();
        for (int i = 0; i < userStockDataList.size(); i++) {
            UserStockData userStockData = userStockDataList.get(i);
            userStocks.add(new Stock(userStockData.getStockId(), userStockData.getStockPrice(),
                    stockNames.get(i), userStockData.getQuantity()));
        }

        return stockData;
    }

    public UserStockData registerTransaction(Order order) {
        UserStockData stockData = new UserStockData();
        User user = findUserById(order.getCustomerId());

        stockData.setStockPrice(order.getStockPrice());
        stockData.setStockId(order.getStockId());
        stockData.setUser(user);
        stockData.setQuantity(order.getQuantity());

        ArrayList<UserStockData> currentOwnedStock = new ArrayList<>(user.getOwnedStocks());
        currentOwnedStock.add(stockData);
        user.setOwnedStocks(currentOwnedStock);
        userRepository.save(user);
        return userStockDataRepository.save(stockData);
    }
}
