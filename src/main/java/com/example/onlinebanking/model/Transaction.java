package com.example.onlinebanking.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DEBIT or CREDIT
    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    private String description;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) { this.account = account; }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }
}