package com.bank.digital_banking.service.impl.transactionRules;

import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.interfaces.TransactionRule;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionValidator {
    private List<TransactionRule> transactionRules;
    public TransactionValidator(List<TransactionRule> transactionRules) {
        this.transactionRules = transactionRules;
    }
    public void validate(Account account, Double amount, TransactionType transactionType){
        for(TransactionRule transactionRule : transactionRules){
            transactionRule.validate(account,amount,transactionType);
        }
    }
}
