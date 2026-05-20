package com.bank.digital_banking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Positive(message = "Limit per transaction must be positive")
    private Double limitPerTransaction;

    @PositiveOrZero(message = "Initial balance must be positive or zero")
    private Double balance;

    @Positive(message = "Daily limit must be greater than 0")
    private Double dailyLimit;

    @Positive(message = "Monthly limit must be greater than 0")
    private Double monthlyLimit;
}