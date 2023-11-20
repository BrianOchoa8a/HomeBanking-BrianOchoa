package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;





    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountService.findAllAccount();
        Stream<Account> accountStream = accounts.stream();
        Stream<AccountDTO> accountDTOStream = accountStream.map(account -> new AccountDTO(account));
        List<AccountDTO> accountDTOS = accountDTOStream.collect(Collectors.toList());
        return  accountDTOS;
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.findAccountById(id);
    }
    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccount(Authentication authentication) {
        String email = authentication.getName();
        Client client= clientService.findClientByEmail(email);
        Set<Account>  accountsSet = client.getAccounts();

        return accountsSet.stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType){
        Client client = clientService.findClientByEmail(authentication.getName());

        if(client.getAccounts().stream().filter(account -> account.getActive()).count() >=3){
            return new ResponseEntity<>("Cannot create any more accounts", HttpStatus.FORBIDDEN);
        }

        Account account = new Account(AccountUtils.generateNumber(), LocalDate.now(), 0,true, accountType);
        client.addAccount(account);
        clientService.saveClient(client);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/account/modify")
    public ResponseEntity<?> accountModify(Authentication authentication, @RequestParam String number) {
        if (authentication.getName() == null) {
            return new ResponseEntity<>("Error Client must be authenticated ", HttpStatus.FORBIDDEN);
        }
        if (number.isBlank()) {
            return new ResponseEntity<>("Error Account error ", HttpStatus.FORBIDDEN);
        }
        if (!accountService.existsAccountByNumber(number)) {
            return new ResponseEntity<>("Error Account error ", HttpStatus.FORBIDDEN);
        }
        Account account = accountService.findAccountByNumber(number);
        Client client = clientService.findClientByEmail(authentication.getName());
        if (account == null) {
            return new ResponseEntity<>("Error Account do not exist ", HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("Account balance must be 0", HttpStatus.FORBIDDEN);
        }
        Boolean verificationAccount = client.getAccounts().stream().noneMatch(account1 -> account1.getNumber().equals(number));
        if (verificationAccount == true) {
            return new ResponseEntity<>("Account balance must be 0", HttpStatus.FORBIDDEN);
        }
        Set<Transaction> transacctions = account.getTransactions().stream().collect(Collectors.toSet());
        transacctions.forEach(transacction -> {
            transacction.setActive(false);
            transactionService.saveTransaction(transacction);
        });
        account.setActive(false);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Account delete", HttpStatus.OK);
    }

}

