package com.hsbc.rbcs.domain;

import com.hsbc.rbcs.infrastructure.dao.entity.Account;
import com.hsbc.rbcs.infrastructure.dao.entity.Transaction;
import com.hsbc.rbcs.infrastructure.dao.repository.AccountRepository;
import com.hsbc.rbcs.infrastructure.dao.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FinancialServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FinancialService financialService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_financial_transaction_ok_when_normal_input() {
        String sourceAccountNumber = "user1";
        String destAccountNumber = "user2";
        BigDecimal amount = BigDecimal.valueOf(100);

        Account sourceAccount = new Account();
        sourceAccount.setAccountNumber(sourceAccountNumber);
        sourceAccount.setBalance(BigDecimal.valueOf(200));

        Account destAccount = new Account();
        destAccount.setAccountNumber(destAccountNumber);
        destAccount.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.findByAccountNumberForUpdate(sourceAccountNumber)).thenReturn(sourceAccount);
        when(accountRepository.findByAccountNumberForUpdate(destAccountNumber)).thenReturn(destAccount);

        Transaction transactionRet = new Transaction();
        transactionRet.setAmount(amount);
        transactionRet.setSourceAccount(sourceAccountNumber);
        transactionRet.setDestAccount(destAccountNumber);
        transactionRet.setTransactionId(UUID.randomUUID().toString());
        transactionRet.setTimestamp(LocalDateTime.now());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionRet);
        Transaction transaction = financialService.transfer(sourceAccountNumber, destAccountNumber, amount);

        assertNotNull(transaction);
        assertEquals(sourceAccountNumber, transaction.getSourceAccount());
        assertEquals(destAccountNumber, transaction.getDestAccount());
        assertEquals(amount, transaction.getAmount());

        verify(accountRepository, times(1)).save(sourceAccount);
        verify(accountRepository, times(1)).save(destAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void test_query_account_info_ok_when_exist() {
        String accountNumber = "user1";
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);

        Account result = financialService.queryByAccount(accountNumber);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(BigDecimal.valueOf(100), result.getBalance());
    }

    @Test
    public void test_init_account_ok_when_account_not_exist() {
        String accountNumber = "user1";
        Double balance = 100.0;
        when(accountRepository.findByAccountNumberForUpdate(accountNumber)).thenReturn(null);
        Account accountRet = new Account();
        accountRet.setAccountNumber(accountNumber);
        accountRet.setBalance(BigDecimal.valueOf(balance));
        when(accountRepository.save(any(Account.class))).thenReturn(accountRet);
        Account result = financialService.initAccount(accountNumber, balance);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(BigDecimal.valueOf(balance), result.getBalance());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void test_init_account_ok_when_account_existed() {
        String accountNumber = "user1";
        Double balance = 100.0;

        Account existingAccount = new Account();
        existingAccount.setAccountNumber(accountNumber);
        existingAccount.setBalance(BigDecimal.valueOf(50));

        when(accountRepository.findByAccountNumberForUpdate(accountNumber)).thenReturn(existingAccount);

        Account accountRet = new Account();
        accountRet.setAccountNumber(accountNumber);
        accountRet.setBalance(BigDecimal.valueOf(balance));
        when(accountRepository.save(any(Account.class))).thenReturn(accountRet);
        Account result = financialService.initAccount(accountNumber, balance);

        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(BigDecimal.valueOf(balance), result.getBalance());

        verify(accountRepository, times(1)).save(existingAccount);
    }
}