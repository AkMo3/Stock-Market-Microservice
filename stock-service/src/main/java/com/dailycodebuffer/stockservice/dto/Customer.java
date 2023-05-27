package com.dailycodebuffer.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
}
