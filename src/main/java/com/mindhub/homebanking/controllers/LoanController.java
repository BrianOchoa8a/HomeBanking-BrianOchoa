package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping(value="/loans")
    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
    Client client = clientService.findClientByEmail(authentication.getName());
    Loan loan = loanService.findLoanById(loanApplicationDTO);

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
        Double amount1= loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*loan.getPorcentageInterest());

        Account accountOrigen = accountService.findAccountByNumber(loanApplicationDTO.getNumberAccount());

        Transaction credit = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), "loan " + loan.getName() + " loan approved", LocalDateTime.now(),accountOrigen.getBalance()+loanApplicationDTO.getAmount(), true);

        ClientLoan prestamo = new ClientLoan(amount1, loanApplicationDTO.getPayments(),amount1,loanApplicationDTO.getPayments());

        accountOrigen.setBalance(accountOrigen.getBalance() + loanApplicationDTO.getAmount());
        accountOrigen.addTransaction(credit);
        client.addClientLoan(prestamo);
        loan.addClientLoan(prestamo);

        accountService.saveAccount(accountOrigen);
        transactionService.saveTransaction(credit);
        clientLoanRepository.save(prestamo);
        loanService.saveLoan(loan);
        clientService.saveClient(client);

        return  new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);
    };

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans() {
        return loanService.findLoanAll();
    }

    @PostMapping("/admin/loans")
    public ResponseEntity<Object> newLoanAdmin(Authentication authentication, @RequestParam String name, @RequestParam Double maxAmount, @RequestParam Double porcentageInterest, @RequestParam List<Integer> payments){
        Client client = clientService.findClientByEmail(authentication.getName());

        if(name.isBlank()){
            return new ResponseEntity<>("Name error", HttpStatus.FORBIDDEN);
        }
        if(maxAmount == null){
            return new ResponseEntity<>("Max Amount not found", HttpStatus.FORBIDDEN);
        }
        if (porcentageInterest==null){
            return new ResponseEntity<>("Porcentage not found", HttpStatus.FORBIDDEN);
        }
        if (payments.isEmpty()){
            return new ResponseEntity<>("Payments error", HttpStatus.FORBIDDEN);
        }
        if(loanService.existsByName(name)){
            return new ResponseEntity<>("This loan already exists", HttpStatus.FORBIDDEN);
        }
        if (maxAmount <= 0){
            return new ResponseEntity<>("Ammount cannot be equal to 0", HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(name,maxAmount,payments,porcentageInterest);
        loanService.saveLoan(loan);
        return  new ResponseEntity<>("Loan Created", HttpStatus.CREATED);
    }

}
