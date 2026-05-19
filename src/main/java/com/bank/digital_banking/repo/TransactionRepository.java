package com.bank.digital_banking.repo;

import com.bank.digital_banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
    List<Transaction> findByAccountId(Long accountId);
}
