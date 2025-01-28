package com.hsbc.rbcs.application;

import com.hsbc.rbcs.domain.FinancialService;
import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.mapper.AccountMapper;
import com.hsbc.rbcs.infrastructure.mapper.TransactionMapper;
import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.HealthCheck200Response;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BalanceApiDelegateImplTest {

    @Mock
    private FinancialService financialService;

    @InjectMocks
    private BalanceApiDelegateImpl balanceApiDelegate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_health_check_ok() {
        ResponseEntity<HealthCheck200Response> response = balanceApiDelegate.healthCheck();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_balance_transaction_ok() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSourceAccount("user1");
        transactionRequest.setDestAccount("user2");
        transactionRequest.setAmount(100.0);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount("user1");
        transaction.setDestAccount("user2");
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTimestamp(LocalDateTime.now());

        when(financialService.transfer(anyString(), anyString(), any(BigDecimal.class))).thenReturn(transaction);

        ResponseEntity<TransactionResponse> response = balanceApiDelegate.balanceTransaction(transactionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().getSourceAccount());
        assertEquals("user2", response.getBody().getDestAccount());
        assertEquals(100.0, response.getBody().getAmount());
    }

    @Test
    public void test_query_balance_ok() {
        String accountNumber = "user1";
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(100));

        when(financialService.queryByAccount(accountNumber)).thenReturn(account);

        ResponseEntity<AccountBalance> response = balanceApiDelegate.queryBalance(accountNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(accountNumber, response.getBody().getAccount());
        assertEquals(100.0, response.getBody().getBalance());
    }

    @Test
    public void test_query_failed_when_account_not_existed() {
        String accountNumber = "user1";

        when(financialService.queryByAccount(accountNumber)).thenReturn(null);

        ResponseEntity<AccountBalance> response = balanceApiDelegate.queryBalance(accountNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void test_init_balance_ok_when_normal_input() {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccount("user1");
        accountBalance.setBalance(100.0);

        Account account = new Account();
        account.setAccountNumber("user1");
        account.setBalance(BigDecimal.valueOf(100));

        when(financialService.initAccount(anyString(), anyDouble())).thenReturn(account);

        ResponseEntity<AccountBalance> response = balanceApiDelegate.initBalance(accountBalance);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("user1", response.getBody().getAccount());
        assertEquals(100.0, response.getBody().getBalance());
    }
}