package com.example.onlinebanking.service;

import com.example.onlinebanking.model.*;
import com.example.onlinebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(UserRepository userRepository,
                          AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Account> getMyAccounts(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return accountRepository.findByUser(user);
    }

    public List<Transaction> getTransactionsForAccount(
            Long accountId, Authentication auth) {

        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Account account = accountRepository.findById(accountId)
                .orElseThrow();

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied to this account");
        }

        return transactionRepository
                .findByAccountOrderByTimestampDesc(account);
    }
}