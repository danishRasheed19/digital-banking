package com.bank.digital_banking.service.interfaces;


import com.bank.digital_banking.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountById(Long id);

    List<Account> getAllAccounts();

    Account deposit(Long id, Double amount);

    Account withdraw(Long id, Double amount);

    void transferMoney(Long fromId, Long toId, Double amount);
}
