package com.bank.digital_banking.utils;

import com.bank.digital_banking.dto.AccountRequestDto;
import com.bank.digital_banking.dto.AccountResponseDto;
import com.bank.digital_banking.model.Account;

public class AccountMapper {

    public static Account toEntity(AccountRequestDto dto) {
        return Account.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .balance(dto.getBalance())
                .limitPerTransaction(dto.getLimitPerTransaction() == null ? 1000.0 : dto.getLimitPerTransaction())
                .dailyLimit(dto.getDailyLimit() == null ? 5000.0 : dto.getDailyLimit())
                .monthlyLimit(dto.getMonthlyLimit() == null ? 10000.0 : dto.getMonthlyLimit())
                .build();
    }

    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .balance(account.getBalance())
                .limitPerTransaction(account.getLimitPerTransaction())
                .dailyLimit(account.getDailyLimit())
                .monthlyLimit(account.getMonthlyLimit())
                .build();
    }
}
