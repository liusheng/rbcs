package com.hsbc.rbcs.application;

import com.hsbc.rbcs.api.BalanceApiDelegate;
import com.hsbc.rbcs.domain.FinancialService;
import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.mapper.AccountMapper;
import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceApiDelegateImpl implements BalanceApiDelegate {

    @Autowired
    private FinancialService financialService;

    @Override
    public ResponseEntity<TransactionResponse> balanceTransaction(TransactionRequest transactionRequest) {
        String result = financialService.transfer(transactionRequest.getSourceAccount(), transactionRequest.getDestAccount(),
                BigDecimal.valueOf(transactionRequest.getAmount()));
        return ResponseEntity.ok(new TransactionResponse());
    }

    @Override
    public ResponseEntity<AccountBalance> queryBalance(String account) {
        Account accountInfo = financialService.queryByAccount(account);
        if (accountInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AccountMapper.INSTANCE.entityToAccountBalance(accountInfo));
    }

    @Override
    public ResponseEntity<AccountBalance> initBalance(AccountBalance accountBalance) {
        Account result = financialService.initAccount(accountBalance.getAccount(), accountBalance.getBalance());
        return ResponseEntity.ok(AccountMapper.INSTANCE.entityToAccountBalance(result));
    }
}