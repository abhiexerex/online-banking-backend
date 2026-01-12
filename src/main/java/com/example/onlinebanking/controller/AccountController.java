package com.example.onlinebanking.controller;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Get all accounts of logged-in user
    @GetMapping("/me")
    public ResponseEntity<List<Account>> getMyAccounts(Authentication auth) {
        return ResponseEntity.ok(accountService.getMyAccounts(auth));
    }
    
    @RestController
    @RequestMapping("/api/user")
    public class UserController {

        @GetMapping("/test")
        public String test() {
            return "JWT WORKING";
        }
    }
    
//    @GetMapping("/balance")
//    public String getBalance() {
//        return "Your balance is 10,000";
//    }


    // Get transactions for specific account (must belong to user)
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(
            @PathVariable Long accountId,
            Authentication auth) {
        return ResponseEntity.ok(
                accountService.getTransactionsForAccount(accountId, auth));
    }
}