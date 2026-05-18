package com.bank.digital_banking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequestDto {
    @NotNull
    private Long fromId;
    @NotNull
    private Long toId;

    @NotNull
    @Positive
    private Double amount;
}
