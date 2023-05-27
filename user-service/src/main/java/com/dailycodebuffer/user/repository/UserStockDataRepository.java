package com.dailycodebuffer.user.repository;

import com.dailycodebuffer.user.VO.StockData;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.entity.UserStockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockDataRepository extends JpaRepository<UserStockData, Long> {
    List<UserStockData> findAllByUser(User user);
}
