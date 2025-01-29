package com.hsbc.rbcs.infrastructure.dao.repository;

import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}