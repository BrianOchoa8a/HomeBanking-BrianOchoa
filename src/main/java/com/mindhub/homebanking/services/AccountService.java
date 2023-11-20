package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {
    Boolean existsAccountByNumber( String number);
    Account findAccountByNumber(String number);
    List<AccountDTO> findAllAccountsDTO ();
    AccountDTO findAccountById(Long id);
    Set<AccountDTO> findAllAccountDTO();
    void saveAccount(Account account);
     List<Account> findAllAccount();
}
