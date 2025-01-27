package com.hsbc.rbcs.domain;


import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.service.AccountRepository;
import com.hsbc.rbcs.infrastructure.dao.service.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public String transfer(String sourceAccountNumber, String destAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountRepository.findByAccountNumberForUpdate(sourceAccountNumber);
        Account destAccount = accountRepository.findByAccountNumberForUpdate(destAccountNumber);

        if (sourceAccount == null || destAccount == null) {
            throw new RuntimeException("Account not found");
        }

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destAccount.setBalance(destAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);

        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setSourceAccount(sourceAccountNumber);
        transaction.setDestAccount(destAccountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Transfer successful";
    }

    public Account queryByAccount(String account) {
        return accountRepository.findByAccountNumber(account);
    }
}