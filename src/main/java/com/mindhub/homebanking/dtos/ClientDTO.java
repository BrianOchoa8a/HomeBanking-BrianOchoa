package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.stream.Collectors;


public class ClientDTO{
        private Long id;
        private String firstName;
        private String lastName;
        private String email;


        private List<AccountDTO> accounts;

        private List<ClientLoanDTO> loans;

        private List<CardDTO> cards;

    public ClientDTO(Client client){
       this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().filter(account -> account.getActive()).map(account -> new AccountDTO(account)).collect(Collectors.toList());
        this.loans = client.getClientLoans().stream().map(c -> new ClientLoanDTO(c)).collect(Collectors.toList());
        this.cards = client.getCards().stream().filter(card -> card.getActive()).map(card -> new CardDTO(card)).collect(Collectors.toList());
    }



        public long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }
}
