package com.bank.digital_banking.service.interfaces;

import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.utils.enums.TransactionType;

public interface TransactionRule {
    void validate(Account account, Double amount, TransactionType transactionType);
}
