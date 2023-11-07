package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Transactional
    @RequestMapping(value="/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
    Client client = clientRepository.findByEmail(authentication.getName());
    Loan loan = loanRepository.findById(loanApplicationDTO.getId()).orElse(null);

        if (loan == null){
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getAmount().isNaN()){
            return new ResponseEntity<>("Enter a valid amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Number of installments not available", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("Requested amount not allowed", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getNumberAccount().isBlank() || client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(loanApplicationDTO.getNumberAccount()))){
            return new ResponseEntity<>("Choose a valid account to make the disbursement", HttpStatus.FORBIDDEN);
        }
        double amount = loanApplicationDTO.getAmount();
        double increase = amount * 0.20;
        double newAmount = amount + increase;

        Account accountOrigen = accountRepository.findByNumber(loanApplicationDTO.getNumberAccount());

        Transaction credit = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), "loan " + loan.getName() + " loan approved", LocalDateTime.now());

        ClientLoan prestamo = new ClientLoan(newAmount, loanApplicationDTO.getPayments());

        accountOrigen.setBalance(accountOrigen.getBalance() + loanApplicationDTO.getAmount());
        accountOrigen.addTransaction(credit);
        client.addClientLoan(prestamo);
        loan.addClientLoan(prestamo);

        accountRepository.save(accountOrigen);
        transactionRepository.save(credit);
        clientLoanRepository.save(prestamo);
        loanRepository.save(loan);
        clientRepository.save(client);

        return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);
    };

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loandb -> new LoanDTO(loandb)).collect(Collectors.toSet());
    }

}
