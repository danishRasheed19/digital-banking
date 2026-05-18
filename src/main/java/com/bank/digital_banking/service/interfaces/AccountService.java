package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.dto.AccountRequestDto;
import com.bank.digital_banking.dto.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto account);

    AccountResponseDto getAccountById(Long id);

    List<AccountResponseDto> getAllAccounts();

    AccountResponseDto deposit(Long id, Double amount);

    AccountResponseDto withdraw(Long id, Double amount);

    void transferMoney(Long fromId, Long toId, Double amount);
}
