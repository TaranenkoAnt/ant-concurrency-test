package ru.spb.taranenkoant.concurrency.lab.domain.model;


import ru.spb.taranenkoant.concurrency.lab.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public class User {

    private Long id;
    private final String userName;
    private BigDecimal balance;

    public User(String userName, BigDecimal balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public boolean canAfford(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public void deductBalance(BigDecimal amount) {
        if (!canAfford(amount)) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        balance = balance.subtract(amount);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
