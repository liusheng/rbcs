package com.hsbc.rbcs.application;

import com.hsbc.rbcs.api.BalanceApiDelegate;
import com.hsbc.rbcs.domain.AccountService;
import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceApiDelegateImpl implements BalanceApiDelegate {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<TransactionResponse> balanceTransaction(TransactionRequest transactionRequest) {
        String result = accountService.transfer(transactionRequest.getSourceAccount(), transactionRequest.getDestAccount(),
                BigDecimal.valueOf(transactionRequest.getAmount()));
        return ResponseEntity.ok(new TransactionResponse());
    }

    @Override
    public ResponseEntity<AccountBalance> queryBalance(String account){
        return ResponseEntity.ok(null);
    }
}