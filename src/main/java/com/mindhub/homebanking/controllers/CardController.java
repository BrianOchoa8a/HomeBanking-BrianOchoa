package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;




    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam String cardColor){
        Client client = clientService.findClientByEmail(authentication.getName());


        if(cardColor.isBlank()){
            return new ResponseEntity<>("Missing data: Error Color Card", HttpStatus.FORBIDDEN);
        }


        if (client.getCards().stream().filter(card -> card.getType() == cardType && card.getActive()).count() >= 3) {
            return new ResponseEntity<>("You cannot have more than three cards of the same type.", HttpStatus.FORBIDDEN);
        }

        Card card = new Card(client.nameCard(), CardType.valueOf(String.valueOf(cardType)), CardColor.valueOf(cardColor), CardUtils.generateNumber(), CardUtils.generateCvv(), LocalDate.now(), LocalDate.now().plusYears(5), true);
        client.addCard(card);
        cardService.saveCard(card);
        clientService.saveClient(client);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/cards/modify")
    public Object cardModify(@RequestParam Long id, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Card modifyCard = cardService.findIdAndClient(id,client);

        if (modifyCard == null) {
            return new ResponseEntity<>("Card not found", HttpStatus.FORBIDDEN);
        }
        modifyCard.setActive(false);
        cardService.saveCard(modifyCard);
        clientService.saveClient(client);
        return new CardDTO(modifyCard);
    }
}