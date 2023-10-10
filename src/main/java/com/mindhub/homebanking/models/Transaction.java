package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private  Account account;

    public Transaction(){

    }

    public Transaction(TransactionType type, Double amount, String description, LocalDate date){

        this.type=type;
        this.amount=amount;
        this.description=description;
        this.date=date;

    }

    public TransactionType getType(){
        return type;
    }

    public void setType(TransactionType type){
        this.type=type;
    }

    public Double getAmount(){
        return amount;
    }

    public void  setAmount (Double amount){
        this.amount=amount;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public  LocalDate getCreationDate (){
        return date;
    }

    public void setDate(LocalDate date){
        this.date=date;
    }

    @JsonIgnore
    public Account getAccount(){
        return account;
    }

    public void setAccount(Account account){
        this.account=account;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", account=" + account +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setAccounts(Account account) {
        this.account= account;
    }

}
