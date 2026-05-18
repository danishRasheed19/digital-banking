package com.bank.digital_banking.dto;

import com.bank.digital_banking.utils.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionResponseDto {
    private Long accountId;
    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime timestamp;
}
