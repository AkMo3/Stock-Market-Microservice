package com.dailycodebuffer.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserStockData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long entryId;
    @ManyToOne private User user;
    private Long StockId;
    private double stockPrice;
    private int quantity;
}
