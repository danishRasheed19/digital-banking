package com.bank.digital_banking.service.impl.transactionRules;

import com.bank.digital_banking.exception.InvalidTransactionException;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.interfaces.TransactionRule;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionAmountRule implements TransactionRule {
    public void validate(Account account, Double amount, TransactionType transactionType){
        if (amount <= 0) {
            throw new InvalidTransactionException("Amount must be greater than zero");
        }
    }
}
