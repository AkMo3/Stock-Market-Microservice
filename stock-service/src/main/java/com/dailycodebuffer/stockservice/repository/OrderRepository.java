package com.dailycodebuffer.stockservice.repository;

import com.dailycodebuffer.stockservice.entity.StockOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<StockOrder, Long> {

}
