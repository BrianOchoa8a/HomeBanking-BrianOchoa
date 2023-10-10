package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getAllClients(){

        List<Client> clients = clientRepository.findAll();
        Stream<Client> clientStream = clients.stream();
        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));
        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());

        return clientDTOS;
    }




    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

}