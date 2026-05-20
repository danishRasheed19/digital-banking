package com.bank.digital_banking.service.impl.transactionRules;

import com.bank.digital_banking.exception.MonthlyLimitExceededException;
import com.bank.digital_banking.model.Account;
import com.bank.digital_banking.service.interfaces.TransactionRule;
import com.bank.digital_banking.service.interfaces.TransactionService;
import com.bank.digital_banking.utils.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class MonthlyLimitRule implements TransactionRule {
    private TransactionService transactionService;
    public MonthlyLimitRule(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void validate(Account account, Double amount, TransactionType transactionType){
        if(transactionType == TransactionType.WITHDRAW || transactionType == TransactionType.TRANSFER_OUT){
            Double totalSpentInMonth = transactionService.getTotalSpentThisMonth(account.getId());
            if(amount + totalSpentInMonth > account.getMonthlyLimit()){
                throw  new MonthlyLimitExceededException("Monthly Limit Exceeded");
            }
        }
    }
}
