package com.hsbc.rbcs.domain;


import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.service.AccountService;
import com.hsbc.rbcs.infrastructure.dao.service.TransactionService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FinancialService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Retry(name = "financialTransactionRetry")
    @Transactional
    public Transaction transfer(String sourceAccountNumber, String destAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountService.findByAccountNumberForUpdate(sourceAccountNumber);
        Account destAccount = accountService.findByAccountNumberForUpdate(destAccountNumber);

        if (sourceAccount == null || destAccount == null) {
            throw new RuntimeException("Account not found");
        }

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destAccount.setBalance(destAccount.getBalance().add(amount));

        accountService.save(sourceAccount);
        accountService.save(destAccount);

        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setSourceAccount(sourceAccountNumber);
        transaction.setDestAccount(destAccountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionService.save(transaction);
    }

    @Retry(name = "accountQueryRetry")
    public Account queryByAccount(String account) {
        return accountService.findByAccountNumber(account);
    }

    @Transactional
    public Account initAccount(String account, Double balance) {
        Account result;
        Account accountData = accountService.findByAccountNumberForUpdate(account);
        if (accountData == null) {
            Account entity = new Account();
            entity.setBalance(BigDecimal.valueOf(balance));
            entity.setAccountNumber(account);
            result = accountService.save(entity);
        } else {
            accountData.setBalance(BigDecimal.valueOf(balance));
            result = accountService.save(accountData);
        }
        return result;
    }
}