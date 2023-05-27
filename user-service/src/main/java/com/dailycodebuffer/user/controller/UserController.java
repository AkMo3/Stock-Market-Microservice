package com.dailycodebuffer.user.controller;

import com.dailycodebuffer.user.VO.Order;
import com.dailycodebuffer.user.VO.StockData;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.entity.UserStockData;
import com.dailycodebuffer.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser of UserController");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockData> getUserWithStocks(@PathVariable("id") Long userId) {
        log.info("Inside getUserWithDepartment of UserController");
        StockData stockData = userService.getUserWithStocks(userId);
        return ResponseEntity.ok(stockData);
    }

    @PostMapping("/transaction/")
    public ResponseEntity<UserStockData> registerTransaction(Order order) {
        return ResponseEntity.ok(userService.registerTransaction(order));
    }

}
