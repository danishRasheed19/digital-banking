package com.bank.digital_banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bank.digital_banking.utils.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> accountNotFoundException(AccountNotFoundException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 404, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> insufficientFundsException(InsufficientFundsException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 400, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(TransactionLimitException.class)
    public ResponseEntity<ErrorResponse> transactionLimitException(TransactionLimitException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 400, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> invalidTransactionException(InvalidTransactionException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 400, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(SameAccountTransferException.class)
    public ResponseEntity<ErrorResponse> sameAccountTransferException(SameAccountTransferException e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 400, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), 500, "Something went wrong");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
