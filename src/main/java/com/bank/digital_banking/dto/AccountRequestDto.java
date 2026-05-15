package com.bank.digital_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDto {
    private String name;
    private String email;
    private Double limitPerTransaction;
    private Double balance;
}
