package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

@Autowired
private LoanRepository loanRepository;

    @Override
    public Loan findLoanById(LoanApplicationDTO loanApplicationDTO) {
        return loanRepository.findById(loanApplicationDTO.getId()).orElse(null);
    }

    @Override
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Set<LoanDTO> findLoanAll() {
        return loanRepository.findAll().stream().map(loandb -> new LoanDTO(loandb)).collect(Collectors.toSet());
    }

    @Override
    public Boolean existsByName(String name) {
        return loanRepository.existsByName(name);
    }
}
