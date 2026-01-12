package com.example.onlinebanking.service;

import com.example.onlinebanking.dto.TransferRequest;
import com.example.onlinebanking.model.*;
import com.example.onlinebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransferService(UserRepository userRepository,
                           AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(TransferRequest request, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Account from = accountRepository
                .findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid from account"));

        Account to = accountRepository
                .findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid to account"));

        // Ensure 'from' account belongs to current user
        if (!from.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only transfer from your own account");
        }

        BigDecimal amount = request.getAmount();

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balances
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Create debit transaction
        Transaction debit = new Transaction();
        debit.setAccount(from);
        debit.setType("DEBIT");
        debit.setAmount(amount);
        debit.setDescription(request.getDescription());
        transactionRepository.save(debit);

        // Create credit transaction
        Transaction credit = new Transaction();
        credit.setAccount(to);
        credit.setType("CREDIT");
        credit.setAmount(amount);
        credit.setDescription(request.getDescription());
        transactionRepository.save(credit);
    }
}