package com.hsbc.rbcs.integration;

import com.hsbc.rbcs.application.BalanceApiDelegateImpl;
import com.hsbc.rbcs.domain.FinancialService;
import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.repository.AccountRepository;
import com.hsbc.rbcs.infrastructure.dao.repository.TransactionRepository;
import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.TransactionRequest;
import com.hsbc.rbcs.model.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private FinancialService financialService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(accountRepository, transactionRepository);
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/balance/v1/health"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBalanceTransaction() throws Exception {
        Account sourceAccount = new Account();
        sourceAccount.setAccountNumber("123");
        sourceAccount.setBalance(BigDecimal.valueOf(200));

        Account destAccount = new Account();
        destAccount.setAccountNumber("456");
        destAccount.setBalance(BigDecimal.valueOf(100));

        Mockito.when(accountRepository.findByAccountNumberForUpdate("123")).thenReturn(sourceAccount);
        Mockito.when(accountRepository.findByAccountNumberForUpdate("456")).thenReturn(destAccount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount("123");
        transaction.setDestAccount("456");
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTimestamp(LocalDateTime.now());
        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSourceAccount("123");
        transactionRequest.setDestAccount("456");
        transactionRequest.setAmount(100.0);

        mockMvc.perform(post("/balance/v1/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sourceAccount\":\"123\",\"destAccount\":\"456\",\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceAccount", is("123")))
                .andExpect(jsonPath("$.destAccount", is("456")))
                .andExpect(jsonPath("$.amount", is(100.0)));
    }

    @Test
    public void testQueryBalance() throws Exception {
        Account account = new Account();
        account.setAccountNumber("123");
        account.setBalance(BigDecimal.valueOf(100));

        Mockito.when(accountRepository.findByAccountNumber("123")).thenReturn(account);

        mockMvc.perform(get("/balance/v1/query-account?account=123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account", is("123")))
                .andExpect(jsonPath("$.balance", is(100.0)));
    }

    @Test
    public void testQueryBalance_NotFound() throws Exception {
        Mockito.when(accountRepository.findByAccountNumber("123")).thenReturn(null);

        mockMvc.perform(get("/balance/v1/query-account?account=123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInitBalance() throws Exception {
        Account account = new Account();
        account.setAccountNumber("123");
        account.setBalance(BigDecimal.valueOf(100));

        Mockito.when(accountRepository.findByAccountNumberForUpdate("123")).thenReturn(null);
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/balance/v1/init-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"account\":\"123\",\"balance\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account", is("123")))
                .andExpect(jsonPath("$.balance", is(100.0)));
    }
}
