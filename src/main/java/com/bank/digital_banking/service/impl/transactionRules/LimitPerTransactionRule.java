package com.bank.digital_banking.service.impl.transactionRules;

import com.bank.digital_banking.exception.TransactionLimitException;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.interfaces.TransactionRule;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class LimitPerTransactionRule implements TransactionRule {
    public void validate(Account account, Double amount, TransactionType transactionType){
        if((transactionType == TransactionType.WITHDRAW || transactionType == TransactionType.TRANSFER_OUT) && account.getLimitPerTransaction() < amount) {
            throw new TransactionLimitException("Transaction Limit Exceeded");
        }
    }
}
