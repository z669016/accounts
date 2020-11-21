package com.putoet.quarkus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class AccountTest {
    private Account account;

    @BeforeEach
    void setup() {
        account = new Account(12345L, 6789L, "TEST CUSTOMER", BigDecimal.ZERO);
    }

    @Test
    void withdraw() {
        account.withdraw(BigDecimal.TEN);
        assertThat(account.getStatus(), equalTo(AccountStatus.OVERDRAWN));
        assertThat(account.getBalance(), equalTo(new BigDecimal("-10")));
    }

    @Test
    void withdrawOnCLosedAccountShouldFail() {
        account.close();
        account.withdraw(BigDecimal.TEN);
        assertThat(account.getStatus(), equalTo(AccountStatus.CLOSED));
        assertThat(account.getBalance(), equalTo(BigDecimal.ZERO));
    }

    @Test
    void deposit() {
        account.deposit(BigDecimal.TEN);
        assertThat(account.getStatus(), equalTo(AccountStatus.OPEN));
        assertThat(account.getBalance(), equalTo(BigDecimal.TEN));
    }

    @Test
    void depositOnCLosedAccountShouldFail() {
        account.close();
        account.deposit(BigDecimal.TEN);
        assertThat(account.getStatus(), equalTo(AccountStatus.CLOSED));
        assertThat(account.getBalance(), equalTo(BigDecimal.ZERO));
    }

    @Test
    void close() {
        account.close();
        assertThat(account.getStatus(), equalTo(AccountStatus.CLOSED));
    }

    @Test
    void available() {
        assertThat(account.isAvailable(), equalTo(true));
        account.close();
        assertThat(account.isAvailable(), equalTo(false));
    }
}