package com.dailycodebuffer.user.entity;

import com.dailycodebuffer.user.VO.Stock;
import com.dailycodebuffer.user.VO.StockData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany private List<UserStockData> ownedStocks;
}
