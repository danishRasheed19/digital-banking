package com.bank.digital_banking.repo;

import com.bank.digital_banking.model.Transaction;
import com.bank.digital_banking.utils.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByAccountIdAndStatus(Long accountId, TransactionStatus status);
    List<Transaction> findByAccountIdAndTimestampAfter(Long accountId, LocalDateTime timestamp);
}
