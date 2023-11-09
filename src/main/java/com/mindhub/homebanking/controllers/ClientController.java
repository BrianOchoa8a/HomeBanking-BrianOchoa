package com.mindhub.homebanking.controllers;

import antlr.BaseAST;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ClientController {

   // @Autowired
    //private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClients(){

        List<Client> clients = clientService.getAllClients();
        Stream<Client> clientStream = clients.stream();
        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));
        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());

        return clientDTOS;
    }




    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.findClientDTOById(id);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String accountNumber(){
        String numberAccount = "VIN" + String.valueOf(getRandomNumber(0, 999));
        while (accountRepository.existsByNumber(numberAccount)){
            numberAccount = "VIN" + String.valueOf(getRandomNumber(0, 999));
        }
        return numberAccount;
    }


    @PostMapping("/clients")
    public ResponseEntity<Object> newClient(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank()) {
            return new ResponseEntity<>("Missing data: First Name Error", HttpStatus.FORBIDDEN);
        }

        if(lastName.isBlank()){
            return new ResponseEntity<>("Missing data: Last Name Error", HttpStatus.FORBIDDEN);
        }

        if(email.isBlank()){
            return new ResponseEntity<>("Missing data: Email Error", HttpStatus.FORBIDDEN);
        }

        if(password.isBlank()){
            return new ResponseEntity<>("Missing data: Password Error", HttpStatus.FORBIDDEN);
        }

        if (!clientService.existsClientByEmail(email)) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }


        Client client = new Client(firstName,lastName,email, passwordEncoder.encode(password), false);
        clientService.saveClient(client);

        Account account = new Account(accountNumber(), LocalDate.now(), 0);
        client.addAccount(account);

        accountRepository.save(account);

        return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));
    }



}
