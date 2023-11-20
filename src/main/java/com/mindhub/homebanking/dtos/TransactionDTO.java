package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    private Double currentBalance;
    private Boolean active;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.currentBalance= transaction.getCurrentBalance();
        this.active=transaction.getActive();
    }

    public  Long getId(){return id;};
    public  TransactionType getType(){return type;};
    public  Double getAmount(){return amount;};
    public  String getDescription(){return description;};
    public  LocalDateTime getDate(){return date;};

    public Boolean getActive() {
        return active;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }
}
