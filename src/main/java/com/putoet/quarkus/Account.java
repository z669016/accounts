package com.putoet.quarkus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Account {
    private Long accountNumber;
    @EqualsAndHashCode.Exclude
    private Long customerNumber;
    @EqualsAndHashCode.Exclude
    private String customerName;
    @EqualsAndHashCode.Exclude
    private BigDecimal balance;
    @EqualsAndHashCode.Exclude
    private AccountStatus status = AccountStatus.OPEN;

    public Account(Long accountNumber, Long customerNumber, String customerName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.balance = balance;
    }

    public void withdraw(BigDecimal amount) {
        if (isAvailable()) {
            balance = balance.subtract(amount);
            mark();
        }
    }

    public void deposit(BigDecimal amount) {
        if (isAvailable()) {
            balance = balance.add(amount);
            mark();
        }
    }

    public void close() {
        status = AccountStatus.CLOSED;
    }

    public boolean isAvailable() {
        return status == AccountStatus.OPEN || status == AccountStatus.OVERDRAWN;
    }

    private void mark() {
        if (BigDecimal.ZERO.compareTo(balance) > 0) {
            markOverdrawn();
        } else {
            unmarkOverdrawn();
        }
    }

    private void markOverdrawn() {
        this.status = AccountStatus.OVERDRAWN;
    }

    private void unmarkOverdrawn() {
        this.status = AccountStatus.OPEN;
    }
}
