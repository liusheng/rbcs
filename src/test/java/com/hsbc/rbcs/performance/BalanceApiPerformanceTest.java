package com.hsbc.rbcs.performance;

import com.hsbc.rbcs.application.BalanceApiDelegateImpl;
import com.hsbc.rbcs.domain.FinancialService;
import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.repository.AccountRepository;
import com.hsbc.rbcs.infrastructure.dao.repository.TransactionRepository;
import com.hsbc.rbcs.infrastructure.dao.service.AccountService;
import com.hsbc.rbcs.infrastructure.dao.service.TransactionService;
import com.hsbc.rbcs.model.AccountBalance;
import com.hsbc.rbcs.model.TransactionRequest;
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
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceApiPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private FinancialService financialService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(accountService, transactionService);
    }

    @Test
    public void testPerformanceBalanceTransaction() throws Exception {
        Account sourceAccount = new Account();
        sourceAccount.setAccountNumber("user1");
        sourceAccount.setBalance(BigDecimal.valueOf(500000000));

        Account destAccount = new Account();
        destAccount.setAccountNumber("user2");
        destAccount.setBalance(BigDecimal.valueOf(100000000));

        Mockito.when(accountService.findByAccountNumberForUpdate("user1")).thenReturn(sourceAccount);
        Mockito.when(accountService.findByAccountNumberForUpdate("user2")).thenReturn(destAccount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount("user1");
        transaction.setDestAccount("user2");
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTimestamp(LocalDateTime.now());
        Mockito.when(transactionService.save(any(Transaction.class))).thenReturn(transaction);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSourceAccount("user1");
        transactionRequest.setDestAccount("user2");
        transactionRequest.setAmount(100.0);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            mockMvc.perform(post("/balance/v1/transaction")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"sourceAccount\":\"user1\",\"destAccount\":\"user2\",\"amount\":2.0}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sourceAccount", is("user1")))
                    .andExpect(jsonPath("$.destAccount", is("user2")))
                    .andExpect(jsonPath("$.amount", is(100.0)));
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Performance test duration: " + duration + " ms");
    }
}
