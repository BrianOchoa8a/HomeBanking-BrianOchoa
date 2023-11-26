package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private Long loanId;
    private String name;
    private Double amount;
    private Integer payments;

    private Double remainAmount;
    private Integer remainPayments;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.remainAmount=clientLoan.getRemainAmount();
        this.remainPayments=clientLoan.getRemainPayments();
    }


    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getRemainAmount() {
        return remainAmount;
    }

    public Integer getRemainPayments() {
        return remainPayments;
    }
}