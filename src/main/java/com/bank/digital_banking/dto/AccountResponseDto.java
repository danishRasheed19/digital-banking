package com.bank.digital_banking.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
    private Long id;
    private String name;
    private String email;
    private Double limitPerTransaction;
    private Double balance;
    private Double dailyLimit;
    private Double monthlyLimit;
}
