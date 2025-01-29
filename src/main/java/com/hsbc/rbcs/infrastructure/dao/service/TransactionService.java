package com.hsbc.rbcs.infrastructure.dao.service;

import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @CachePut(value = "transactions", key = "#transactionData.transactionId")
    public Transaction save(Transaction transactionData) {
        return transactionRepository.save(transactionData);
    }
}
