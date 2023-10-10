package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDate date;

    public  TransactionDTO(Transaction transaction){
        id  = transaction.getId();
        type = transaction.getType();
        amount = transaction.getAmount();
        description = transaction.getDescription();
        date = transaction.getCreationDate();
    }

    public  Long getId(){return id;};
    public  TransactionType getType(){return type;};
    public  Double getAmount(){return amount;};
    public  String getDescription(){return description;};
    public  LocalDate getDate(){return date;};
}
