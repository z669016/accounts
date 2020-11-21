package com.putoet.quarkus;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
public class AccountsResource {
    private static final List<Account> INITIAL_ACCOUNT_LIST = List.of(
            new Account(123456789L, 987654321L, "George Baird", new BigDecimal("354.23")),
            new Account(121212121L, 888777666L, "Mary Taylor", new BigDecimal("560.03")),
            new Account(545454545L, 222444999L, "Diana Rigg", new BigDecimal("422.00")));

    private Map<Long,Account> accounts = new HashMap<>();

    @PostConstruct
    void init() {
        accounts.putAll(INITIAL_ACCOUNT_LIST.stream().collect(Collectors.toMap(Account::getAccountNumber, account -> account)));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Account> accounts() {
        return accounts.values();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account account(@PathParam("id") Long accountNumber) {
        if (accounts.containsKey(accountNumber))
            return accounts.get(accountNumber);

        throw new NotFoundException("Account with accountNumber " + accountNumber + " not found.");
    }
}
