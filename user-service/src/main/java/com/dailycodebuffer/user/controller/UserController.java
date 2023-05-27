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

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkCustomerExist(@PathVariable("id") Long userId) {
        log.info("Inside checkCustomerExist of UserController");
        Boolean exist = userService.findUserById(userId) != null;
        return ResponseEntity.ok(exist);
    }

    @PostMapping("/transaction/")
    public ResponseEntity<Boolean> registerTransaction(@RequestBody Order order) {
        userService.registerTransaction(order);
        return ResponseEntity.accepted().body(Boolean.TRUE);
    }

}
