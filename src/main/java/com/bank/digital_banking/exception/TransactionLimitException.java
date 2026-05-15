package com.bank.digital_banking.exception;

public class TransactionLimitException extends RuntimeException{
    public TransactionLimitException(String message){
        super(message);
    }
}
