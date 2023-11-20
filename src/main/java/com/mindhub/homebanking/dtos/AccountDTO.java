package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private Boolean active;
    private AccountType accountType;

    private List<TransactionDTO> transactions;

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account
                .getTransactions()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
        this.active = account.getActive();
        this.accountType=account.getAccountType();
    }

    public long getId() {
        return id;
    }


    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Boolean getActive() {
        return active;
    }

    public List<TransactionDTO> getTransactions(){return transactions;}

    public AccountType getAccountType() {
        return accountType;
    }
}
