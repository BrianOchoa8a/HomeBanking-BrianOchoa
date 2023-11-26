package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ClientLoanController {

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/loan/payments")
    public ResponseEntity<Object> payLoan (@RequestParam Long id, @RequestParam String accountNumber, Authentication authentication) {


        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>("Client must be authenticated", HttpStatus.FORBIDDEN);
        }

        if (id == null || accountNumber.isBlank()) {
            return new ResponseEntity<>("Missing information", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findAccountByNumber(accountNumber);
        ClientLoan clientLoan = clientLoanService.findById(id);
        Double payment = clientLoan.getAmount() / clientLoan.getPayments();

        if (client == null) {
            return new ResponseEntity<>("Client do not exits", HttpStatus.FORBIDDEN);
        }

        if (account == null) {
            return new ResponseEntity<>("Account do not exits", HttpStatus.FORBIDDEN);
        }

        if (account.getBalance() < payment) {
            return new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
        }

        if(clientLoan.getRemainAmount() <=0){
            return new ResponseEntity<>("Fully Paid loan", HttpStatus.FORBIDDEN);
        }
        Transaction loanTransaction = new Transaction(TransactionType.DEBIT,payment,"Loan payment", LocalDateTime.now(),account.getBalance()-payment,true);
        account.setBalance(account.getBalance()-payment);
        clientLoan.setRemainPayments(clientLoan.getRemainPayments()-1);
        clientLoan.setRemainAmount(clientLoan.getRemainAmount()-payment);
        account.addTransaction(loanTransaction);

        accountService.saveAccount(account);
        clientLoanService.saveClientLoan(clientLoan);
        transactionService.saveTransaction(loanTransaction);

        return new ResponseEntity<>("Loan payment ok",HttpStatus.OK);
    }

    @GetMapping("/clientLoans/{id}")
    public ResponseEntity<?> clientLoanById(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Client not authenticated", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findClientByEmail(authentication.getName());
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        ClientLoan clientLoan = clientLoanService.findById(id);
        if (clientLoan == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
        }

        if (!client.getClientLoans().contains(clientLoan)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new ClientLoanDTO(clientLoan), HttpStatus.OK);
    }

}
