package com.bank.digital_banking.service.impl.transactionRules;

import com.bank.digital_banking.exception.DailyLimitExceededException;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.interfaces.TransactionRule;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class DailyLimitRule implements TransactionRule {
    private final TransactionService transactionService;
    public DailyLimitRule(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void validate(Account account, Double amount, TransactionType transactionType){
        if(transactionType == TransactionType.WITHDRAW || transactionType == TransactionType.TRANSFER_OUT){
            Double totalSpentToday = transactionService.getTotalSpentToday(account.getId());
            if(amount + totalSpentToday > account.getDailyLimit()){
                throw new DailyLimitExceededException("Daily Limit Exceeded");
            }
        }
    }
}
