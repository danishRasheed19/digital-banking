package com.bank.digital_banking.exception;

public class MonthlyLimitExceededException extends RuntimeException {
    public MonthlyLimitExceededException(String message) {
        super(message);
    }
}
