package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private Long id;

    private String numberAccount;

    private Double amount;
    private Integer payments;

    public LoanApplicationDTO(Long id, Double amount, Integer payments, String numberAccount) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.numberAccount = numberAccount;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumberAccount() {
        return numberAccount;
    }
}
