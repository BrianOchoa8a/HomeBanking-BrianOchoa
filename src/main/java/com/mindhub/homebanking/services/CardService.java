package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    Boolean existsCardByNumber (String number);

    void saveCard (Card card);

    Card findIdAndClient (Long id, Client client);


}
