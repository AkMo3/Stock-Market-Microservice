package com.dailycodebuffer.stockservice.repository;

import com.dailycodebuffer.stockservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
