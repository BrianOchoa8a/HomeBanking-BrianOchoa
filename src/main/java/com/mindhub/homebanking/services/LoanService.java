package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.Set;

public interface LoanService {

    Loan findLoanById(LoanApplicationDTO loanApplicationDTO);
    Loan saveLoan(Loan loan);
    Set<LoanDTO> findLoanAll ();
    Boolean existsByName(String name);
}
