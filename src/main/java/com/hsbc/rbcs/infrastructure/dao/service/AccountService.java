package com.hsbc.rbcs.infrastructure.dao.service;

import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Cacheable(value = "accounts", key = "#accountNumber")
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @CachePut(value = "accounts", key = "#accountData.accountNumber")
    public Account save(Account accountData) {
        return accountRepository.save(accountData);
    }

    // 此方法不适用缓存机制
    public Account findByAccountNumberForUpdate(String accountNumber) {
        return accountRepository.findByAccountNumberForUpdate(accountNumber);
    }

}
